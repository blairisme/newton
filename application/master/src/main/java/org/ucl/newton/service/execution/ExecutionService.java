/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.execution;

import org.ucl.newton.bridge.ExecutionResult;
import org.ucl.newton.framework.Experiment;

/**
 * Implementors of this interface provide methods that start and stop
 * experiment execution.
 *
 * @author Blair Butterworth
 */
public interface ExecutionService
{
    void beginExecution(Experiment experiment);

    void cancelExecution(Experiment experiment);

    void executionComplete(ExecutionResult executionResult);

    boolean isExecutionComplete(int experimentId);

    boolean isExecutionComplete(Experiment experiment);
}
