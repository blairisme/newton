/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.api.project;

import org.springframework.web.bind.annotation.*;
import org.ucl.newton.framework.Project;
import org.ucl.newton.framework.ProjectBuilder;
import org.ucl.newton.service.project.ProjectService;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Instances of this class expose project service methods via REST.
 *
 * @author Blair Butterworth
 */
@RestController
@SuppressWarnings("unused")
public class ProjectApi
{
    private ProjectService projectService;
    private UserService userService;

    @Inject
    public ProjectApi(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @RequestMapping(value = "/api/project", method = RequestMethod.POST)
    public void addProject(@RequestBody ProjectDto project) {
        ProjectBuilder projectBuilder = new ProjectBuilder();
        projectBuilder.fromProjectDto(project);
        projectBuilder.setOwner(userService.getUserByEmail(project.getOwner()));
        projectBuilder.setMembers(userService.getUsersByEmail(project.getMembers()));
        projectService.addProject(projectBuilder.build());
    }

    @RequestMapping(value = "/api/project/{identifier}", method = RequestMethod.GET)
    public ProjectDto getProject(@PathVariable String identifier) {
        return ProjectDtoBuilder.fromProject(projectService.getProjectByIdentifier(identifier, true));
    }

    @RequestMapping(value = "/api/projects", method = RequestMethod.GET)
    public ProjectDtoSet getProjects() {
        Collection<Project> projects = projectService.getProjects();
        List<ProjectDto> result = projects.stream().map(ProjectDtoBuilder::fromProject).collect(Collectors.toList());
        return new ProjectDtoSet(result);
    }

    @RequestMapping(value = "/api/project/{identifier}", method = RequestMethod.DELETE)
    public void removeProject(@PathVariable String identifier) {
        Project project = projectService.getProjectByIdentifier(identifier, true);
        projectService.removeProject(project);
    }
}
