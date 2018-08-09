/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.acceptance.gherkin;

import org.ucl.newton.common.lang.Strings;
import org.ucl.newton.integration.acceptance.newton.project.ProjectDto;

import java.util.Collection;
import java.util.List;

/**
 * Contains information about a project in the Newton system.
 *
 * @author Blair Butterworth
 */
public class Project
{
    private String identifier;
    private String name;
    private String description;
    private String owner;
    private String members;
    private String dataSources;

    public Project() {
    }

    public Project(
        String identifier,
        String name,
        String description,
        String owner,
        String members,
        String dataSources)
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

    public String getMembers() {
        return members;
    }

    public String getDataSources() {
        return dataSources;
    }

    public ProjectDto asProjectDto() {
        Collection<String> membership = split(members);
        Collection<String> sources = split(dataSources);
        return new ProjectDto(identifier, name, description, owner, membership, sources);
    }

    private Collection<String> split(String value) {
        List<String> patterns = Strings.split(value, ",");
        return Strings.trim(patterns);
    }
}
