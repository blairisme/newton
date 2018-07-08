/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.execution;

import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.framework.Experiment;

import java.util.UUID;

/**
 * Instances of this class build {@link ExecutionRequest} objects.
 *
 * @author Blair Butterworth
 */
public class ExecutionRequestBuilder
{
    public static ExecutionRequest forExperiment(Experiment experiment) {
        return new ExecutionRequest(
                UUID.randomUUID().toString(),
                experiment.getId(),
                "test.py",
                "https://github.com/ziad-alhalabi/python-test/archive/master.zip",
                0,
                "*.py");
    }
}
