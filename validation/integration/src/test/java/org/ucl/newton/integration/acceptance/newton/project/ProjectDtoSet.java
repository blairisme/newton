/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.acceptance.newton.project;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Represents a data transfer object containing a collection of
 * {@link ProjectDto ProjectDtos}.
 *
 * @author Blair Butterworth
 */
public class ProjectDtoSet
{
    private List<ProjectDto> projects;

    public ProjectDtoSet() {
    }

    public ProjectDtoSet(List<ProjectDto> projects) {
        this.projects = projects;
    }

    public List<ProjectDto> getProjects() {
        return projects;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        ProjectDtoSet that = (ProjectDtoSet)obj;
        return new EqualsBuilder()
            .append(this.projects, that.projects)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(projects)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("projects", projects)
            .toString();
    }
}
