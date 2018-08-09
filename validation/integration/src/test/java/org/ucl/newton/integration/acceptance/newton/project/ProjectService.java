/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.acceptance.newton.project;

import org.ucl.newton.common.network.RestException;
import org.ucl.newton.common.network.RestRequest;
import org.ucl.newton.common.network.RestResponse;
import org.ucl.newton.common.network.RestServer;

import java.util.Collection;

/**
 * Provides access to project services the Newton REST API.
 *
 * @author Blair Butterworth
 */
public class ProjectService
{
    private RestServer server;

    public ProjectService(RestServer server) {
        this.server = server;
    }

    public void addProject(ProjectDto project) throws RestException {
        RestRequest request = server.post(ProjectResource.Project);
        request.setBody(project, ProjectDto.class);
        request.make();
    }

    public void addProjects(Collection<ProjectDto> projects) throws RestException {
        for (ProjectDto project: projects) {
            addProject(project);
        }
    }

    public Collection<ProjectDto> getProjects() throws RestException {
        RestRequest request = server.get(ProjectResource.Projects);
        RestResponse response = request.make();
        ProjectDtoSet projectSet = response.asType(ProjectDtoSet.class);
        return projectSet.getProjects();
    }

    public void removeProject(ProjectDto project) throws RestException {
        RestRequest request = server.delete("project/" + project.getIdentifier());
        request.make();
    }

    public void removeProjects(Collection<ProjectDto> projects) throws RestException {
        for (ProjectDto project: projects) {
            removeProject(project);
        }
    }
}
