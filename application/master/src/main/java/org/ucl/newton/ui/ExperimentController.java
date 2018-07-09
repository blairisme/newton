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
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.User;
import org.ucl.newton.service.execution.ExecutionService;
import org.ucl.newton.service.experiment.ExperimentService;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;

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
            ExecutionService executionService) {
        this.userService = userService;
        this.experimentService = experimentService;
        this.executionService = executionService;
    }

    @RequestMapping(value = "/project/{projectName}/experiment/{experimentId}", method = RequestMethod.GET)
    public String details(@PathVariable("projectName") String projectName,
                                    @PathVariable("experimentId") int experimentId, ModelMap model) {
        User user = userService.getAuthenticatedUser();
        model.addAttribute("user", user);
        Experiment selectedExperiment = experimentService.getExperimentById(experimentId);
        model.addAttribute("experiment", selectedExperiment);
        model.addAttribute("project", selectedExperiment.getProject());
        return "project/experiment/overview";
    }

    @RequestMapping(value = "/project/{projectName}/experiment/{experimentId}/run", method = RequestMethod.GET)
    public String execute(@PathVariable("projectName") String projectName,
                          @PathVariable("experimentId") int experimentId, ModelMap model) {
        executionService.run(experimentService.getExperimentById(experimentId));
        return "redirect:/project/" + projectName + "/experiment/" + Integer.toString(experimentId);
    }
}
