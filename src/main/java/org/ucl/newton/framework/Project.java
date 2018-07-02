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

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Instances of this class represent a research project, a container for
 * experiments and associated data sources.
 *
 * @author Blair Butterworth
 */
@Entity
@Table(name = "projects")
public class Project implements Serializable
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "link")
    private String link;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "updated")
    private Date updated;

    @ManyToOne
    private User owner;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "project_membership",
        joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Collection<User> members;

    public Project() {
    }

    public Project(
        int id,
        String link,
        String name,
        String description,
        Date updated,
        User owner,
        Collection<User> members)
    {
        this.id = id;
        this.link = link;
        this.name = name;
        this.updated = updated;
        this.description = description;
        this.owner = owner;
        this.members = members;
    }

    public int getId() {
        return id;
    }

    public Project setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getLastUpdated() {
        return updated;
    }

    public long getLastUpdatedAsEpoch() {
        return updated.getTime();
    }

    public String getLastUpdatedDescription() {
        PrettyTime timeFormatter = new PrettyTime();
        return timeFormatter.format(updated);
    }

    public User getOwner() {
        return owner;
    }

    public Collection<User> getMembers() {
        return members;
    }

    public void setMembers(Collection<User> newMember) {
        this.members = members;
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
            .append(owner, project.owner)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(name)
            .append(description)
            .append(updated)
            .append(owner)
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
