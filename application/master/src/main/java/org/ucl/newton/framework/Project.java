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
import java.util.Collection;
import java.util.Date;

/**
 * Instances of this class represent a research project, a container for
 * experiments and associated data sources.
 *
 * @author Blair Butterworth
 * @author John Wilkie
 */
@Entity
@Table(name = "projects")
public class Project implements Serializable
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "updated")
    private Date updated;

    @ManyToOne
    private User owner;

    @OneToMany
    @JoinTable(name = "project_membership",
        joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Collection<User> members;

    @OneToMany
    @JoinTable(name = "project_starred",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_starred_project")),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_starred_user"))
    )
    private Collection<User> membersThatStar;

    @ElementCollection
    @CollectionTable(name="project_datasources", joinColumns=@JoinColumn(name="pds_project"))
    @Column(name = "pds_datasource")
    private Collection<String> dataSources;

    public Project() {
    }

    public Project(
        int id,
        String identifier,
        String name,
        String description,
        String image,
        Date updated,
        User owner,
        Collection<User> members,
        Collection<String> dataSources)
    {
        this.id = id;
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.image = image;
        this.updated = updated;
        this.owner = owner;
        this.members = members;
        this.dataSources = dataSources;
    }

    public int getId() {
        return id;
    }

    public Project setId(int id) {
        this.id = id;
        return this;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getLastUpdated() {
        return updated;
    }

    public void setLastUpdated( Date lastUpdated) {
        this.updated = lastUpdated;
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

    public void setMembers(Collection<User> members) {
        this.members = members;
    }

    public Collection<User> getMembersThatStar() {
        return membersThatStar;
    }

    public void setMembersThatStar(Collection<User> membersThatStar) {
        this.membersThatStar = membersThatStar;
    }

    public Collection<String> getDataSources() {
        return dataSources;
    }

    public void setDataSources(Collection<String> dataSources) {
        this.dataSources = dataSources;
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
            .append(identifier, project.identifier)
            .append(name, project.name)
            .append(description, project.description)
            .append(image, project.image)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(identifier)
            .append(name)
            .append(description)
            .append(image)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("identifier", identifier)
            .append("name", name)
            .append("description", description)
            .append("image", image)
            .append("updated", updated)
            .toString();
    }
}
