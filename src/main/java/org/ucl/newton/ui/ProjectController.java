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
import org.ucl.newton.framework.User;
import org.ucl.newton.service.ExperimentService;
import org.ucl.newton.service.ProjectService;
import org.ucl.newton.service.UserService;

import javax.inject.Inject;

/**
 * Instances of this class provide an MVC controller for web pages used to
 * list and manage projects.
 *
 * @author Blair Butterworth
 */
@Controller
@Scope("session")
@RequestMapping("/")
@SuppressWarnings("unused")
public class ProjectController
{
    private UserService userService;
    private ProjectService projectService;
    private ExperimentService experimentService;

    @Inject
    public ProjectController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
        this.experimentService = new ExperimentService();
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String list(ModelMap model) {
        User user = userService.getAuthenticatedUser();
        model.addAttribute("user", user);
        model.addAttribute("projects", projectService.getProjects(user));
        return "ProjectList";
    }

    @RequestMapping(value = "/project/{name}", method = RequestMethod.GET)
    public String details(@PathVariable("name")String name, ModelMap model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("project", projectService.getProject(name));
        model.addAttribute("experiments",experimentService.getExperiments());
        return "ProjectDetails_new";
    }
}
