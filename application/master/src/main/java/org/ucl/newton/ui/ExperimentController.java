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
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermissions;
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
        Experiment temp = createExperiment(experimentDto, experimentIdentifier, projectIdentifier);
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
        StorageType type = StorageType.Newton;
        String script = experimentDto.getSelectedTypeValue().equals("newton-jupyter") ? "main.ipynb" : "main.py";
        return new StorageConfiguration(0, type, location, script);
    }

    private void populateRepository(Experiment experiment) {
        try {
            Resource template = new ClassPathResource("/templates");
            Resource repository = experiment.getConfiguration().getStorageConfiguration().getStorageLocation();

            FileUtils.copyDirectory(template.getFile(), repository.getFile());

            System.out.println("\n\n\n\n\n\n***");
            File[] files = repository.getFile().listFiles();
            if(files!=null){
                System.out.println("files not null");
                for(int i=0; i<files.length; i++){
                    System.out.println("file= "+files[i].getName()+" path"+files[i].getAbsolutePath());
                    Files.setPosixFilePermissions(files[i].toPath(), PosixFilePermissions.fromString("rwxrwxrwx"));
                }

                for(int i=0; i<files.length; i++){
                    System.out.println("\n\n\nfile= "+files[i].getName()+" path"+Files.getPosixFilePermissions(files[i].toPath()));
                }
            }else{
                System.out.println("files are empty");
            }


            System.out.println("\n\n\n\n\n\n***");
        } catch (IOException exception) {
            exception.printStackTrace(); //log this
        }
    }
}
