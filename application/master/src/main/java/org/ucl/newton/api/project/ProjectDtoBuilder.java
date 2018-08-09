/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.api.project;

import org.apache.commons.lang3.Validate;
import org.ucl.newton.framework.Project;
import org.ucl.newton.framework.User;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Builds {@link ProjectDto} objects from a given {@link Project}.
 *
 * @author Blair Butterworth
 */
public class ProjectDtoBuilder
{
    private String identifier;
    private String name;
    private String description;
    private String owner;
    private Collection<String> members;
    private Collection<String> dataSources;

    public static ProjectDto fromProject(Project project) {
        ProjectDtoBuilder builder = new ProjectDtoBuilder();
        builder.setIdentifier(project.getIdentifier());
        builder.setName(project.getName());
        builder.setDescription(project.getDescription());
        builder.setOwner(project.getOwner());
        builder.setMembers(project.getMembers());
        builder.setDataSources(project.getDataSources());
        return builder.build();
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(User owner) {
        setOwner(owner.getEmail());
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setMembers(Collection<User> members) {
        setMemberIds(members.stream().map(User::getEmail).collect(Collectors.toList()));
    }

    public void setMemberIds(Collection<String> members) {
        this.members = members;
    }

    public void setDataSources(Collection<String> dataSources) {
        this.dataSources = dataSources;
    }

    public ProjectDto build() {
        Validate.notNull(identifier);
        Validate.notNull(name);
        Validate.notNull(description);
        Validate.notNull(owner);
        Validate.notNull(dataSources);
        return new ProjectDto(identifier, name, description, owner, members, dataSources);
    }
}
