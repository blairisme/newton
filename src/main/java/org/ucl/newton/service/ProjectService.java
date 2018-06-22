/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service;

import org.ucl.newton.framework.Project;
import org.ucl.newton.framework.User;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Instances of this interface provide access to project data.
 *
 * @author Blair Butterworth
 */
@Named
public class ProjectService
{
    private List<Project> projects;

    public ProjectService() {
        projects = new ArrayList<>();
        projects.add(new Project("project-fizzyo", "Project Fizzyo"));
        projects.add(new Project("cancer-research", "Cancer Research Trial 4"));
    }

    public Collection<Project> getProjects(User user) {
        return projects;
    }

    public Project getProject(String id) {
        for (Project project: projects) {
            if (Objects.equals(id, project.getId())){
                return project;
            }
        }
        throw new IllegalStateException("Unknown Project - " + id);
    }
}
