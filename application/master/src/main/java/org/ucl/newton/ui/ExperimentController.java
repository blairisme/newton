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
    private ExperimentService experimentService;
    private UserService userService;

    @Inject
    public ExperimentController(ExperimentService experimentService, UserService userService) {
        this.experimentService = experimentService;
        this.userService = userService;
    }

    @RequestMapping(value = "/project/{projectName}/experiment/{experimentId}", method = RequestMethod.GET)
    public String experimentDetails(@PathVariable("projectName") String projectName,
                                    @PathVariable("experimentId") int experimentId, ModelMap model) {
        User user = userService.getAuthenticatedUser();
        model.addAttribute("user", user);
        Experiment selectedExperiment = experimentService.getExperimentById(experimentId);
        model.addAttribute("experiment", selectedExperiment);
        model.addAttribute("project", selectedExperiment.getParentProject());
        return "project/experiment/overview";
    }
}
