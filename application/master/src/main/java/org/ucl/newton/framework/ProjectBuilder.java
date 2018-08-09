/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.framework;

import org.apache.commons.lang3.Validate;
import org.ucl.newton.api.project.ProjectDto;
import org.ucl.newton.common.identifier.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Instances of this class construct {@link Project} instances.
 *
 * @author Blair Butterworth
 */
public class ProjectBuilder
{
    private String identifier;
    private String name;
    private String description;
    private String image;
    private Date updated;
    private User owner;
    private Collection<User> members;
    private Collection<String> dataSources;

    public ProjectBuilder() {
        this.description = "";
        this.image = "default.png";
        this.updated = new Date();
        this.members = new ArrayList<>();
        this.dataSources = new ArrayList<>();
    }

    public void fromProjectDto(ProjectDto projectDto) {
        setName(projectDto.getName());
        setIdentifier(Identifier.create(projectDto.getIdentifier()));
        setDescription(projectDto.getDescription());
        setDataSources(projectDto.getDataSources());
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

    public void setImage(String image) {
        this.image = image;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public void setOwner(User owner) {
        this.owner = owner;
        this.members.add(owner);
    }

    public void setMembers(Collection<User> members) {
        this.members.addAll(members);
    }

    public void setDataSources(Collection<String> dataSources) {
        this.dataSources = dataSources;
    }

    public Project build() {
        Validate.notNull(identifier);
        Validate.notNull(name);
        Validate.notNull(owner);
        return new Project(0, identifier, name, description, image, updated, owner, members, dataSources);
    }
}
