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
import java.util.Collection;

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

    public Project addProject(Project project) {
        return repository.addProject(project);
    }

    public void mergeProject(Project project) { repository.mergeProject(project); }

    public Collection<Project> getProjects(User user) {
        return repository.getProjects(user);
    }

    public Collection<Project> getStarredProjects(User user) { return repository.getProjectsStarredByUser(user); }

    public Project getProjectById(int id) {
        Project project = repository.getProjectById(id);
        if (project == null) {
            throw new UnknownProjectException(id);
        }
        return project;
    }

    public Project getProjectByIdentifier(String identifier, boolean eagerly) {
        Project project;
        if(eagerly) {
            project = repository.getProjectEagerlyByIdentifier(identifier);
        } else {
            project = repository.getProjectByIdentifier(identifier);
        }

        if (project == null) {
            throw new UnknownProjectException(identifier);
        }
        return project;
    }

    public void persistUnstar(String projectIdentifier, User user) {
        Project project = getProjectByIdentifier(projectIdentifier, true);
        project.getMembersThatStar().remove(user);
        repository.updateProject(project);
    }

    public void persistStar(String projectIdentifier, User user) {
        Project project = getProjectByIdentifier(projectIdentifier, true);
        project.getMembersThatStar().add(user);
        repository.updateProject(project);
    }

    public void persistAddMemberToProject(String projectIdentifier, User user) {
        Project project = getProjectByIdentifier(projectIdentifier, true);
        project.getMembers().add(user);
        repository.updateProject(project);
    }

    public void persistRemoveMemberFromProject(String projectIdentifier, User user) {
        Project project = getProjectByIdentifier(projectIdentifier, true);
        project.getMembers().remove(user);
        repository.updateProject(project);
    }
}
