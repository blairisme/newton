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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.ExperimentVersion;
import org.ucl.newton.framework.User;
import org.ucl.newton.service.execution.ExecutionService;
import org.ucl.newton.service.experiment.ExperimentService;
import org.ucl.newton.service.jupyter.JupyterServer;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;
import java.net.URI;

/**
 * Instances of this class provide an MVC controller for web pages used to
 * list and manage experiments.
 *
 * @author Blair Butterworth
 * @author John Wilkie
 */
@Controller
@Scope("session")
@SuppressWarnings("unused")
public class ExperimentController
{
    private UserService userService;
    private ExperimentService experimentService;
    private ExecutionService executionService;
    private JupyterServer jupyterServer;

    @Inject
    public ExperimentController(
        UserService userService,
        ExperimentService experimentService,
        ExecutionService executionService,
        JupyterServer jupyterServer)
    {
        this.userService = userService;
        this.experimentService = experimentService;
        this.executionService = executionService;
        this.jupyterServer = jupyterServer;
    }

    @GetMapping(value = "/project/{project}/{experiment}")
    public String details(
        @PathVariable("project") String projectIdentifier,
        @PathVariable("experiment") String experimentIdentifier,
        @RequestParam(name = "version", required = false, defaultValue = "latest") String version,
        ModelMap model)
    {
        Experiment experiment = experimentService.getExperimentByIdentifier(experimentIdentifier);

        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("experiment", experiment);
        model.addAttribute("project", experiment.getProject());
        model.addAttribute("version", getVersion(experiment, version));
        model.addAttribute("executing", !executionService.isExecutionComplete(experiment));

        return "experiment/overview";
    }

    @GetMapping(value = "/project/{project}/new")
    public String newExperiment(@PathVariable("project") String projectIdentifier, ModelMap model){
        model.addAttribute("user", userService.getAuthenticatedUser());
        return "experiment/new";
    }

    private ExperimentVersion getVersion(Experiment experiment, String version) {
        if (! experiment.hasVersion()) {
            return null;
        }
        if (version.equalsIgnoreCase("latest")) {
            return experiment.getLatestVersion();
        }
        return experiment.getVersion(Integer.parseInt(version));
    }

    @GetMapping(value = "/project/{project}/{experiment}/run")
    public String run(
        @PathVariable("project") String projectIdentifier,
        @PathVariable("experiment") String experimentIdentifier,
        ModelMap model)
    {
        executionService.beginExecution(experimentService.getExperimentByIdentifier(experimentIdentifier));
        return "redirect:/project/" + projectIdentifier + "/" + experimentIdentifier;
    }

    @GetMapping(value = "/project/{project}/{experiment}/edit")
    public String edit(
            @PathVariable("project") String project,
            @PathVariable("experiment") String experiment,
            ModelMap model)
    {
        User user = userService.getAuthenticatedUser();
        URI editorUrl = jupyterServer.getEditorUrl(user, experiment);
        String redirectPath = editorUrl.toString();
        return "redirect:" + redirectPath;
    }
}
