/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.ucl.newton.framework.Project;
import org.ucl.newton.framework.User;
import org.ucl.newton.service.experiment.ExperimentService;
import org.ucl.newton.service.project.ProjectService;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;
import java.util.ArrayList;

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

    @Inject
    public ProjectController(
            UserService userService,
            ProjectService projectService,
            ExperimentService experimentService)
    {
        this.userService = userService;
        this.projectService = projectService;
        this.experimentService = experimentService;
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String list(ModelMap model) {
        User user = userService.getAuthenticatedUser();
        model.addAttribute("user", user);
        model.addAttribute("projects", projectService.getProjects(user));
        return "project/list";
    }

    @RequestMapping(value = "/project/{name}", method = RequestMethod.GET)
    public String details(@PathVariable("name")String name, ModelMap model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("project", projectService.getProject(name));
        model.addAttribute("experiments",experimentService.getExperiments());
        return "project/details";
    }

    @RequestMapping(value = "/project/{name}/settings", method = RequestMethod.GET)
    public String setting(@PathVariable("name")String name, ModelMap model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("project", projectService.getProject(name));
        return "project/settings";
    }

    @RequestMapping(value = "/project/{name}/members", method = RequestMethod.GET)
    public String members(@PathVariable("name")String name, ModelMap model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("project", projectService.getProject(name));
        return "project/members";
    }

    @RequestMapping(value = "/project/new", method = RequestMethod.GET)
    public String newProject(ModelMap model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "project/new";
    }

    @RequestMapping(value = "/project/new", method = RequestMethod.POST)
    public String createProject(ModelMap model) {
        System.out.println("i'm here!");
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "project/list";
    }
}
