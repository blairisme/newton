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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.ucl.newton.engine.ExecutionEngine;
import org.ucl.newton.framework.*;
import org.ucl.newton.service.experiment.ExperimentService;
import org.ucl.newton.service.jupyter.JupyterServer;
import org.ucl.newton.service.plugin.PluginService;
import org.ucl.newton.service.project.ProjectService;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

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
    private ExecutionEngine executionEngine;
    private JupyterServer jupyterServer;
    private ProjectService projectService;
    private PluginService pluginService;

    @Inject
    public ExperimentController(
        UserService userService,
        ExperimentService experimentService,
        ExecutionEngine executionEngine,
        JupyterServer jupyterServer,
        ProjectService projectService,
        PluginService pluginService)
    {
        this.userService = userService;
        this.experimentService = experimentService;
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

    @GetMapping(value = "/project/{project}/new")
    public String newExperiment(@PathVariable("project") String projectName, ModelMap model){
        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("project", projectService.getProjectByIdentifier(projectName, true));
        model.addAttribute("experiment", new ExperimentDto());
        model.addAttribute("triggerValues", new String[] {"Manual", "On data change"});
        model.addAttribute("storageValues", new String[] {"Newton"});
        model.addAttribute("typeValues", pluginService.getDataProcessors());
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
        Experiment experiment = experimentService.getExperimentByIdentifier(experimentIdentifier);
        executionEngine.startExecution(experiment);
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

    @PostMapping(value = "/project/{project}/new")
    public String persistNewExperiment(
            @PathVariable("project") String projectName,
            @ModelAttribute("experiment") @Valid ExperimentDto experimentDto,
            BindingResult result,
            ModelMap model)
    {
        ExperimentBuilder builder = new ExperimentBuilder();
        builder.setName(experimentDto.getName());
        builder.generateIdentifier(experimentDto.getName());
        builder.setDescription(experimentDto.getDescription());
        builder.setCreator(userService.getAuthenticatedUser());
        builder.setProject(projectService.getProjectByIdentifier(projectName, true));
        builder.setExperimentVersions(new ArrayList<>());
        builder.setConfiguration(createExperimentConfiguration(experimentDto));
        experimentService.addExperiment(builder.build());

        return "redirect:/project/" + projectName;
    }

    private ExperimentConfiguration createExperimentConfiguration(ExperimentDto experimentDto) {
        ExperimentConfigurationBuilder builder = new ExperimentConfigurationBuilder();
        //builder.setStorageConfiguration(new StorageConfiguration(0, experimentDto.getSelectedStorageValue(), "file:/experiment/experimentid/repository",""));
        builder.setStorageConfiguration(new StorageConfiguration(0, experimentDto.getSelectedStorageValue(), "classpath:/experiment/experiment-1/repository","main.py"));
        builder.setProcessorPluginId(experimentDto.getSelectedTypeValue(), pluginService.getDataProcessors());
        builder.addDataSources(experimentDto.getDataSourceIds(), experimentDto.getDataSourceLocs());
        builder.setOutputPattern(experimentDto.getOutputPattern());
        builder.addTrigger(experimentDto.getSelectedTriggerValue());
        return builder.build();
    }

}
