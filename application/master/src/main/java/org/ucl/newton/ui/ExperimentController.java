/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ucl.newton.api.experiment.ExperimentDto;
import org.ucl.newton.engine.ExecutionEngine;
import org.ucl.newton.framework.*;
import org.ucl.newton.service.experiment.ExperimentOperations;
import org.ucl.newton.service.experiment.ExperimentService;
import org.ucl.newton.service.jupyter.JupyterServer;
import org.ucl.newton.service.plugin.PluginService;
import org.ucl.newton.service.project.ProjectService;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
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
    private static Logger logger = LoggerFactory.getLogger(ExperimentController.class);

    private UserService userService;
    private ExperimentService experimentService;
    private ExperimentOperations experimentOperations;
    private ExecutionEngine executionEngine;
    private JupyterServer jupyterServer;
    private ProjectService projectService;
    private PluginService pluginService;

    @Inject
    public ExperimentController(
        UserService userService,
        ExperimentService experimentService,
        ExperimentOperations experimentOperations,
        ExecutionEngine executionEngine,
        JupyterServer jupyterServer,
        ProjectService projectService,
        PluginService pluginService)
    {
        this.userService = userService;
        this.experimentService = experimentService;
        this.experimentOperations = experimentOperations;
        this.executionEngine = executionEngine;
        this.jupyterServer = jupyterServer;
        this.projectService = projectService;
        this.pluginService = pluginService;
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
        model.addAttribute("executing", !executionEngine.isExecutionComplete(experiment));

        return "experiment/overview";
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

    @GetMapping(value = "/project/{project}/new")
    public String newExperiment(@PathVariable("project") String projectName, ModelMap model) {
        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("project", projectService.getProjectByIdentifier(projectName, true));
        model.addAttribute("experiment", new ExperimentDto());
        model.addAttribute("triggerValues", new String[] {"Manual", "On data change"});
        model.addAttribute("storageValues", new String[] {"Newton"});
        model.addAttribute("typeValues", pluginService.getDataProcessors());
        return "experiment/new";
    }

    @GetMapping(value = "/project/{project}/{experiment}/run")
    public String run(
        @PathVariable("project") String projectIdentifier,
        @PathVariable("experiment") String experimentIdentifier,
        ModelMap model)
    {
        Experiment experiment = experimentService.getExperimentByIdentifier(experimentIdentifier);
        executionEngine.startExecution(experiment);
        return "redirect:/project/" + projectIdentifier + "/" + experimentIdentifier;
    }

    @GetMapping(value = "/project/{project}/{experiment}/cancel")
    public String cancel(
        @PathVariable("project") String projectIdentifier,
        @PathVariable("experiment") String experimentIdentifier,
        ModelMap model)
    {
        Experiment experiment = experimentService.getExperimentByIdentifier(experimentIdentifier);
        executionEngine.stopExecution(experiment);
        return "redirect:/project/" + projectIdentifier + "/" + experimentIdentifier;
    }

    @GetMapping(value = "/project/{project}/{experiment}/setup")
    public String experimentSetup(
            @PathVariable("project") String projectIdentifier,
            @PathVariable("experiment") String experimentIdentifier,
            ModelMap model)
    {
        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("project", projectService.getProjectByIdentifier(projectIdentifier, true));
        model.addAttribute("experiment", experimentService.getExperimentByIdentifier(experimentIdentifier));
        model.addAttribute("triggerValues", new ExperimentTriggerType[] {ExperimentTriggerType.Manual, ExperimentTriggerType.Onchange});
        model.addAttribute("storageValues", new StorageType[] {StorageType.Newton});
        model.addAttribute("typeValues", pluginService.getDataProcessors());
        model.addAttribute("experimentDto", new ExperimentDto());
        return "experiment/setup";
    }

    @PostMapping(value = "/project/{project}/{experiment}/setup")
    public String experimentUpdate(
            @PathVariable("project") String projectIdentifier,
            @PathVariable("experiment") String experimentIdentifier,
            @ModelAttribute("experimentDto") @Valid ExperimentDto experimentDto,
            RedirectAttributes redirectAttr)
    {
        try {
            Experiment toUpdate = experimentService.getExperimentByIdentifier(experimentIdentifier);
            Experiment temp = experimentOperations.createExperiment(experimentDto, experimentIdentifier);
            toUpdate.setDescription(temp.getDescription());
            toUpdate.getConfiguration().setTrigger(temp.getConfiguration().getTrigger());
            toUpdate.getConfiguration().setOutputPattern(temp.getConfiguration().getOutputPattern());
            toUpdate.getConfiguration().setExperimentDataSources(temp.getConfiguration().getExperimentDataSources());
            experimentService.updateExperiment(toUpdate);
        } catch (Throwable e) {
            redirectAttr.addFlashAttribute("message", "Update failed " + e.getMessage());
            redirectAttr.addFlashAttribute("alertClass", "alert-danger");
            return "redirect:/project/" + projectIdentifier + "/" + experimentIdentifier + "/setup";
        }
        redirectAttr.addFlashAttribute("message", "Update was successful");
        redirectAttr.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/project/" + projectIdentifier + "/" + experimentIdentifier + "/setup";
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

    @PostMapping(value = "/project/{project}/new")
    public String persistNewExperiment(
        @PathVariable("project") String projectId,
        @ModelAttribute("experiment") @Valid ExperimentDto experimentDto,
        ModelMap model)
    {
        Experiment experiment = experimentOperations.createExperiment(experimentDto);
        experimentService.addExperiment(experiment);
        experimentOperations.populateRepository(experiment);

        return "redirect:/project/" + projectId;
    }
}
