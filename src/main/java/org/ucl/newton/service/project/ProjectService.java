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

    public void addProject(Project project) {
        repository.addProject(project);
    }

    public Collection<Project> getProjects(User user) {
        return repository.getProjects(user);
    }

    public Project getProjectById(int id) {
        Project project = repository.getProjectById(id);
        if (project == null) {
            throw new UnknownProjectException(id);
        }
        return project;
    }

    public Project getProjectByLink(String link) {
        Project project = repository.getProjectByLink(link);
        if (project == null) {
            throw new UnknownProjectException(link);
        }
        return project;
    }
}
