/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

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
public class ExecutionRestController
{
    private ExecutionService executionService;

    @Inject
    public ExecutionRestController(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @RequestMapping(value = "/api/experiment/iscomplete", method = RequestMethod.GET)
    public Boolean isExecutionComplete(@RequestParam int experimentId) {
        return executionService.isExecutionComplete(experimentId);
    }
}
