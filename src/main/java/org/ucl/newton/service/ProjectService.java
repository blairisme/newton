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
import java.util.*;
import java.util.concurrent.TimeUnit;

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
        projects.add(new Project("project-fizzyo", "Project Fizzyo", new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(12)), 2));
        projects.add(new Project("cancer-research", "Cancer Research Trial 4", new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(7)), 0));
        projects.add(new Project("aids-research", "AIDS Research", new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(2)), 7));
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
