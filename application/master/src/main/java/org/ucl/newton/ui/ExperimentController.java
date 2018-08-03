/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.common.identifier.Identifier;
import org.ucl.newton.engine.ExecutionEngine;
import org.ucl.newton.framework.*;
import org.ucl.newton.service.experiment.ExperimentService;
import org.ucl.newton.service.experiment.ExperimentStorage;
import org.ucl.newton.service.jupyter.JupyterServer;
import org.ucl.newton.service.plugin.PluginService;
import org.ucl.newton.service.project.ProjectService;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;

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
    private ExperimentStorage experimentStorage;
    private ExecutionEngine executionEngine;
    private JupyterServer jupyterServer;
    private ProjectService projectService;
    private PluginService pluginService;


    @Inject
    public ExperimentController(
        UserService userService,
        ExperimentService experimentService,
        ExperimentStorage experimentStorage,
        ExecutionEngine executionEngine,
        JupyterServer jupyterServer,
        ProjectService projectService,
        PluginService pluginService)
    {
        this.userService = userService;
        this.experimentService = experimentService;
        this.experimentStorage = experimentStorage;
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
        String experimentId = Identifier.create(experimentDto.getName());
        Experiment experiment = createExperiment(experimentDto, experimentId, projectId);

        experimentService.addExperiment(experiment);
        populateRepository(experiment);

        return "redirect:/project/" + projectId;
    }

    private Experiment createExperiment(ExperimentDto experimentDto, String experimentId, String projectId) {
        ExperimentBuilder builder = new ExperimentBuilder();
        builder.setName(experimentDto.getName());
        builder.setIdentifier(experimentId);
        builder.setDescription(experimentDto.getDescription());
        builder.setCreator(userService.getAuthenticatedUser());
        builder.setProject(projectService.getProjectByIdentifier(projectId, true));
        builder.setExperimentVersions(new ArrayList<>());
        builder.setConfiguration(createConfiguration(experimentDto, experimentId));
        return builder.build();
    }

    private ExperimentConfiguration createConfiguration(ExperimentDto experimentDto, String experimentId) {
        ExperimentConfigurationBuilder builder = new ExperimentConfigurationBuilder();
        builder.setStorageConfiguration(createStorageConfiguration(experimentDto, experimentId));
        builder.setProcessorPluginId(experimentDto.getSelectedTypeValue(), pluginService.getDataProcessors());
        builder.addDataSources(experimentDto.getDataSourceIds(), experimentDto.getDataSourceLocs());
        builder.setOutputPattern(experimentDto.getOutputPattern());
        builder.setDisplayPattern(experimentDto.getSelectedTypeValue().equals("newton-jupyter") ? "*.html" : "");
        builder.addTrigger(experimentDto.getSelectedTriggerValue());
        return builder.build();
    }

    private StorageConfiguration createStorageConfiguration(ExperimentDto experimentDto, String experimentId) {
        String location = experimentStorage.getRepositoryPath(experimentId).toString();
        String type = experimentDto.getSelectedStorageValue();
        String script = experimentDto.getSelectedTypeValue().equals("newton-jupyter") ? "main.ipynb" : "main.py";
        return new StorageConfiguration(0, type, location, script);
    }

    private void populateRepository(Experiment experiment) {
        try {
            Resource template = new ClassPathResource("/templates");
            Resource repository = experiment.getConfiguration().getStorageConfiguration().getStorageLocation();
            FileUtils.copyDirectory(template.getFile(), repository.getFile());
        } catch (IOException exception) {
            exception.printStackTrace(); //log this
        }
    }
}
