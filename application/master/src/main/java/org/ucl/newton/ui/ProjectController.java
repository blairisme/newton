/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.common.identifier.Identifier;
import org.ucl.newton.framework.Project;
import org.ucl.newton.framework.ProjectBuilder;
import org.ucl.newton.framework.User;
import org.ucl.newton.sdk.provider.DataSource;
import org.ucl.newton.service.data.DataPermissionService;
import org.ucl.newton.service.experiment.ExperimentService;
import org.ucl.newton.service.plugin.PluginService;
import org.ucl.newton.service.project.ProjectService;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.ucl.newton.common.lang.Integers.stringToInt;
import static org.ucl.newton.common.lang.Objects.ensureNotNull;

/**
 * Instances of this class provide an MVC controller for web pages used to
 * list and manage projects.
 *
 * @author Blair Butterworth
 */
@Controller
@Scope("session")
@SuppressWarnings("unused")
public class ProjectController
{
    private UserService userService;
    private ProjectService projectService;
    private ExperimentService experimentService;
    private ApplicationStorage applicationStorage;
    private PluginService pluginService;
    private DataPermissionService dataPermissionService;

    @Inject
    public ProjectController(
        UserService userService,
        ProjectService projectService,
        ExperimentService experimentService,
        ApplicationStorage applicationStorage,
        PluginService pluginService,
        DataPermissionService dataPermissionService)
    {
        this.userService = userService;
        this.projectService = projectService;
        this.experimentService = experimentService;
        this.applicationStorage = applicationStorage;
        this.pluginService = pluginService;
        this.dataPermissionService = dataPermissionService;
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String list(ModelMap model) {
        User user = userService.getAuthenticatedUser();
        model.addAttribute("user", user);
        model.addAttribute("projects", projectService.getProjects(user));
        model.addAttribute("starredProjects", projectService.getStarredProjects(user));
        return "project/list";
    }

    @RequestMapping(value = "/project/{name}", method = RequestMethod.GET)
    public String details(@PathVariable("name")String name, ModelMap model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("project", projectService.getProjectByIdentifier(name, true));
        model.addAttribute("experiments", experimentService.getExperimentsByProject(name));
        return "project/details";
    }

    @RequestMapping(value ="/project/{name}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void changeProjectStar(@PathVariable("name")String name,
            @RequestParam String type,
            ModelMap model)
    {
        User user = userService.getAuthenticatedUser();
        if(Objects.equals(type, "Unstar")) {
            projectService.removeStar(name, user);
        } else if(Objects.equals(type, "Star")) {
            projectService.addStar(name, user);
        }
    }

    @RequestMapping(value = "/project/{name}/settings", method = RequestMethod.GET)
    public String setting(@PathVariable("name")String name, ModelMap model) {
        User user = userService.getAuthenticatedUser();
        model.addAttribute("user", user);
        model.addAttribute("project", projectService.getProjectByIdentifier(name, true));
        model.addAttribute("dataPermissions", dataPermissionService.getAllPermissionsForUser(user));
        model.addAttribute("dataSources", getDataSourcesMappedById());
        return "project/settings";
    }

    @RequestMapping(value = "/project/new", method = RequestMethod.GET)
    public String newProject(ModelMap model) {
        User user = userService.getAuthenticatedUser();
        model.addAttribute("user", user);
        model.addAttribute("dataPermissions", dataPermissionService.getAllPermissionsForUser(user));
        model.addAttribute("dataSources", getDataSourcesMappedById());
        return "project/new";
    }

    private Map<String, DataSource> getDataSourcesMappedById() {
        return pluginService.getDataSources().stream().collect(Collectors.toMap(DataSource::getIdentifier, Function.identity()));
    }

    @PostMapping(value = "/project/{name}/delete")
    public String deleteProject(
            @PathVariable("name") String projectIdentifier)
    {
        Project toDelete = projectService.getProjectByIdentifier(projectIdentifier, true);
        projectService.removeProject(toDelete);
        return "redirect:/projects";
    }

    @PostMapping("/project/new")
    public String persistNewProject(
        @RequestParam String name,
        @RequestParam(required=false) String description,
        @RequestParam(required=false) MultipartFile image,
        @RequestParam(required=false) Collection<String> members,
        @RequestParam(required=false) Collection<String> sources,
        ModelMap model)
    {
        try {
            ProjectBuilder projectBuilder = new ProjectBuilder();
            projectBuilder.setName(name);
            projectBuilder.setIdentifier(Identifier.create(name));
            projectBuilder.setDescription(description);
            projectBuilder.setImage(persistProjectImage(image));
            projectBuilder.setOwner(userService.getAuthenticatedUser());
            projectBuilder.setMembers(userService.getUsers(stringToInt(ensureNotNull(members))));
            projectBuilder.setDataSources(sources);
            projectService.addProject(projectBuilder.build());
            return "redirect:/projects";
        }
        catch (Throwable exception) {
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("user", userService.getAuthenticatedUser());
            return "project/new";
        }
    }

    @PostMapping("/project/{ident}/update")
    public String updateProject(
            @PathVariable("ident")String projectIdentifier,
            @RequestParam(required=false) String description,
            @RequestParam(required=false) MultipartFile image,
            @RequestParam(required=false) Collection<String> members,
            @RequestParam(required=false) Collection<String> sources,
            ModelMap model) {
        try {
            Project projectToUpdate = projectService.getProjectByIdentifier(projectIdentifier, true);
            projectToUpdate.setDescription(description);
            if(image != null && image.getOriginalFilename().length() != 0
                    && !image.getOriginalFilename().equals(projectToUpdate.getImage())) {
                projectToUpdate.setImage(persistProjectImage(image));
            }
            projectToUpdate.setMembers(userService.getUsers(stringToInt(ensureNotNull(members))));
            projectToUpdate.setDataSources(sources);
            projectToUpdate.setLastUpdated(new Date());
            projectService.updateProject(projectToUpdate);
        } catch (Throwable exception) {
            model.addAttribute("error", "Error: " + exception.getMessage());
            return "project/settings";
        }
        return "redirect:/project/" + projectIdentifier + "/settings";
    }


    private String persistProjectImage(MultipartFile image) throws IOException {
        if (image != null && ! image.isEmpty()) {
            try (InputStream stream = image.getInputStream()) {
                String identifier = generateImageName(image);
                write("images/project", identifier, stream);
                return identifier;
            }
        }
        return "default.png";
    }

    private void write(String group, String identifier, InputStream inputStream) throws IOException {
        try (OutputStream outputStream = applicationStorage.getOutputStream(Paths.get(group, identifier))) {
            IOUtils.copy(inputStream, outputStream);
        }
    }

    private String generateImageName(MultipartFile image) {
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        String identifier = UUID.randomUUID().toString();
        return identifier + "." + extension;
    }
}
