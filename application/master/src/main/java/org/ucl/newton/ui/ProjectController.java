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
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.framework.ProjectBuilder;
import org.ucl.newton.framework.User;
import org.ucl.newton.service.experiment.ExperimentService;
import org.ucl.newton.service.plugin.PluginService;
import org.ucl.newton.service.project.ProjectService;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

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

    @Inject
    public ProjectController(
        UserService userService,
        ProjectService projectService,
        ExperimentService experimentService,
        ApplicationStorage applicationStorage,
        PluginService pluginService)
    {
        this.userService = userService;
        this.projectService = projectService;
        this.experimentService = experimentService;
        this.applicationStorage = applicationStorage;
        this.pluginService = pluginService;
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
        model.addAttribute("experiments", experimentService.getExperimentsByParentProjectName(name));
        return "project/details";
    }

    @RequestMapping(value ="/project/{name}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void changeProjectStar(@PathVariable("name")String name,
            @RequestParam String type,
            ModelMap model)
    {
        User user = userService.getAuthenticatedUser();
        if( type.equals("Unstar")) {
            projectService.persistUnstar(name, user);
        } else if(type.equals("Star")) {
            projectService.persistStar(name, user);
        }
    }

    @RequestMapping(value = "/project/{name}/settings", method = RequestMethod.GET)
    public String setting(@PathVariable("name")String name, ModelMap model) {
        User user = userService.getAuthenticatedUser();
        model.addAttribute("user", user);
        model.addAttribute("project", projectService.getProjectByIdentifier(name, true));
        model.addAttribute("starredProjects", projectService.getStarredProjects(user));
        return "project/settings";
    }

    @RequestMapping(value = "/project/new", method = RequestMethod.GET)
    public String newProject(ModelMap model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("dataProviders", pluginService.getDataProviders());
       // model.addAttribute("sources", new ArrayList<String>());
        return "project/new";
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
            projectBuilder.generateIdentifier(name);
            projectBuilder.setName(name);
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

    private String persistProjectImage(MultipartFile image) throws IOException {
        if (image != null && ! image.isEmpty()) {
            try (InputStream stream = image.getInputStream()) {
                String identifier = generateImageName(image);
                applicationStorage.write("images/project", identifier, stream);
                return identifier;
            }
        }
        return "default.png";
    }

    private String generateImageName(MultipartFile image) {
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        String identifier = UUID.randomUUID().toString();
        return identifier + "." + extension;
    }
}
