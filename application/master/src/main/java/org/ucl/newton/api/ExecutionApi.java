/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ucl.newton.engine.ExecutionEngine;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.service.experiment.ExperimentService;

import javax.inject.Inject;

/**
 * Instances of this class expose execution service methods via REST.
 *
 * @author Blair Butterworth
 */
@RestController
@SuppressWarnings("unused")
public class ExecutionApi
{
    private ExecutionEngine executionEngine;
    private ExperimentService experimentService;

    @Inject
    public ExecutionApi(ExecutionEngine executionEngine, ExperimentService experimentService) {
        this.executionEngine = executionEngine;
        this.experimentService = experimentService;
    }

    @RequestMapping(value = "/api/experiment/iscomplete", method = RequestMethod.GET)
    public Boolean isExecutionComplete(@RequestParam("experiment") String identifier) {
        Experiment experiment = experimentService.getExperimentByIdentifier(identifier);
        return executionEngine.isExecutionComplete(experiment);
    }
}
