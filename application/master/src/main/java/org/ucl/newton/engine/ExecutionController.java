/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.engine;

import org.ucl.newton.framework.Experiment;

/**
 * Implementors of this interface
 *
 * @author Blair Butterworth
 */
public interface ExecutionController
{
    void startExecution(Experiment experiment);

    void stopExecution(Experiment experiment);

    boolean isExecutionComplete(Experiment experiment);
}
