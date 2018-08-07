/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.api.project;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;

/**
 * Instances of this class represent a data transfer object for project
 * information.
 *
 * @author Blair Butterworth
 */
public class ProjectDto
{
    private String identifier;
    private String name;
    private String description;
    private String owner;
    private Collection<String> members;
    private Collection<String> dataSources;

    public ProjectDto() {
    }

    public ProjectDto(
        String identifier,
        String name,
        String description,
        String owner,
        Collection<String> members,
        Collection<String> dataSources)
    {
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.members = members;
        this.dataSources = dataSources;
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

    public String getOwner() {
        return owner;
    }

    public Collection<String> getMembers() {
        return members;
    }

    public Collection<String> getDataSources() {
        return dataSources;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        ProjectDto that = (ProjectDto)obj;
        return new EqualsBuilder()
            .append(identifier, that.identifier)
            .append(name, that.name)
            .append(description, that.description)
            .append(owner, that.owner)
            .append(members, that.members)
            .append(dataSources, that.dataSources)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(identifier)
            .append(name)
            .append(description)
            .append(owner)
            .append(members)
            .append(dataSources)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("identifier", identifier)
            .append("name", name)
            .append("description", description)
            .append("owner", owner)
            .append("members", members)
            .append("dataSources", dataSources)
            .toString();
    }
}
