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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.ExperimentVersion;
import org.ucl.newton.framework.Project;
import org.ucl.newton.service.execution.ExecutionService;
import org.ucl.newton.service.experiment.ExperimentService;
import org.ucl.newton.service.user.UserService;
import javax.inject.Inject;
import java.util.List;
import java.util.Objects;

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

    @Inject
    public ExperimentController(
        UserService userService,
        ExperimentService experimentService,
        ExecutionService executionService)
    {
        this.userService = userService;
        this.experimentService = experimentService;
        this.executionService = executionService;
    }

    @RequestMapping(value = "/project/{projectName}/experiment/{experimentId}", method = RequestMethod.GET)
    public String details(@PathVariable("projectName") String projectName,
                          @PathVariable("experimentId") int experimentId, ModelMap model)
    {
        Experiment experiment = experimentService.getExperimentById(experimentId);
        Project project1 = experiment.getProject();
        ExperimentVersion version = getVersion(experiment, "latest");

        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("experiment", experiment);
        model.addAttribute("project", project1);
        model.addAttribute("version", version);
        model.addAttribute("latestVersion", version);
        model.addAttribute("executing", !executionService.isExecutionComplete(experiment));

        return "project/experiment/overview";
    }

    @GetMapping(value = "/project/{project}/{experiment}/{version}")
    public String details(
        @PathVariable("project") String project,
        @PathVariable("experiment") int experimentId,
        @PathVariable("version") String versionId,
        ModelMap model)
    {
        Experiment experiment = experimentService.getExperimentById(experimentId);
        Project project1 = experiment.getProject();
        ExperimentVersion version = getVersion(experiment, versionId);

        model.addAttribute("user", userService.getAuthenticatedUser());
        model.addAttribute("experiment", experiment);
        model.addAttribute("project", project1);
        model.addAttribute("version", version);
        model.addAttribute("latestVersion", version);
        model.addAttribute("executing", !executionService.isExecutionComplete(experiment));

        return "project/experiment/overview";
    }

    private ExperimentVersion getVersion(Experiment experiment, String version) {
        List<ExperimentVersion> versions = experiment.getVersions();
        if (versions.isEmpty()) {
            return null;
        }
        int versionIndex = version.equals("latest") ? versions.size() - 1 : Integer.parseInt(version);
        return versions.get(versionIndex);
    }

    @RequestMapping(value = "/project/{projectName}/experiment/{experimentId}/run", method = RequestMethod.GET)
    public String execute(@PathVariable("projectName") String projectName,
                          @PathVariable("experimentId") int experimentId, ModelMap model)
    {
        executionService.beginExecution(experimentService.getExperimentById(experimentId));
        return "redirect:/project/" + projectName + "/experiment/" + Integer.toString(experimentId);
    }

}
