/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.framework;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.ocpsoft.prettytime.PrettyTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;

/**
 * Instances of this class represent a research project, a container for
 * experiments and associated data sources.
 *
 * @author Blair Butterworth
 */
@Entity
@Table(name = "PROJECTS")
public class Project implements Serializable
{
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "updated")
    private Date updated;

    public Project() {
    }

    public Project(String id, String name, String description, Date updated) {
        this.id = id;
        this.name = name;
        this.updated = updated;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getLastUpdated() {
        return updated;
    }

    public void setLastUpdated(Date updated){
        this.updated = updated;
    }

    public String getLastUpdatedDescription() {
        PrettyTime timeFormatter = new PrettyTime();
        return timeFormatter.format(updated);
    }

    public Collection<User> getMembers() {
        return Collections.emptyList();
    }

    public int getStars() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Project project = (Project)obj;
        return new EqualsBuilder()
                .append(id, project.id)
                .append(name, project.name)
                .append(description, project.description)
                .append(updated, project.updated)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(description)
                .append(updated)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("description", description)
                .append("updated", updated)
                .toString();
    }
}
