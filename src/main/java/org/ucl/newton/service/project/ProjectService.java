/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.project;

import org.ucl.newton.framework.Project;
import org.ucl.newton.framework.User;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 * Instances of this interface provide access to project data.
 *
 * @author Blair Butterworth
 */
@Named
public class ProjectService
{
    private ProjectRepository repository;

    @Inject
    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public Collection<Project> getProjects(User user) {
        return repository.getProjects(0, 20);
    }

    public Project getProject(String id) {
        Project project = repository.getProject(id);
        if (project == null) {
            throw new IllegalStateException("Unknown project - " + id);
        }
        return project;
    }
}
