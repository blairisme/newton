/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.project;

import org.apache.commons.lang3.Validate;
import org.ucl.newton.framework.Project;
import org.ucl.newton.framework.User;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 * Instances of this interface provide access to project data.
 *
 * @author Blair Butterworth
 * @author John Wilkie
 */
@Named
public class ProjectService
{
    private ProjectRepository repository;

    @Inject
    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    public Project addProject(Project project) {
        Validate.notNull(project);
        return repository.addProject(project);
    }

    public void addStar(String projectIdentifier, User user) {
        Validate.notNull(projectIdentifier);
        Validate.notNull(user);

        Project project = getProjectByIdentifier(projectIdentifier, true);
        project.getMembersThatStar().add(user);
        repository.updateProject(project);
    }

    public void addMemberToProject(String projectIdentifier, User user) {
        Validate.notNull(projectIdentifier);
        Validate.notNull(user);

        Project project = getProjectByIdentifier(projectIdentifier, true);
        project.getMembers().add(user);
        repository.updateProject(project);
    }

    public Collection<Project> getProjects() {
        return repository.getProjects();
    }

    public Collection<Project> getProjects(User user) {
        Validate.notNull(user);
        return repository.getProjects(user);
    }

    public Collection<Project> getStarredProjects(User user) {
        Validate.notNull(user);
        return repository.getProjectsStarredByUser(user);
    }

    public Project getProjectById(int id) {
        Project project = repository.getProjectById(id);
        if (project == null) {
            throw new UnknownProjectException(id);
        }
        return project;
    }

    public Project getProjectByIdentifier(String identifier, boolean eagerly) {
        Project project;
        if (eagerly) {
            project = repository.getProjectEagerlyByIdentifier(identifier);
        }
        else {
            project = repository.getProjectByIdentifier(identifier);
        }
        if (project == null) {
            throw new UnknownProjectException(identifier);
        }
        return project;
    }

    public void removeStar(String projectIdentifier, User user) {
        Validate.notNull(projectIdentifier);
        Validate.notNull(user);

        Project project = getProjectByIdentifier(projectIdentifier, true);
        project.getMembersThatStar().remove(user);
        repository.updateProject(project);
    }

    public void removeMemberFromProject(String projectIdentifier, User user) {
        Validate.notNull(projectIdentifier);
        Validate.notNull(user);

        Project project = getProjectByIdentifier(projectIdentifier, true);
        project.getMembers().remove(user);
        repository.updateProject(project);
    }

    public void removeProject(Project project) {
        Validate.notNull(project);
        repository.removeProject(project);
    }

    public void updateProject(Project project) {
        Validate.notNull(project);
        repository.mergeProject(project);
    }
}
