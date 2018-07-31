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
import org.ucl.newton.service.execution.ExecutionService;

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
    private ExecutionService executionService;

    @Inject
    public ExecutionApi(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @RequestMapping(value = "/api/experiment/iscomplete", method = RequestMethod.GET)
    public Boolean isExecutionComplete(@RequestParam String experiment) {
        return executionService.isExecutionComplete(experiment);
    }
}
