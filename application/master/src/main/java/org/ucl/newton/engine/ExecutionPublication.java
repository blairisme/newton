/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.engine;

import javax.inject.Named;

/**
 * Delivers the results of experiment execution to an (optionally) configured
 * destination.
 *
 * @author Blair Butterworth
 */
@Named
public class ExecutionPublication extends ExecutionPipelineBase
{
    @Override
    public void process(ExecutionTask task) {
        finish(task);
    }

    @Override
    public void cancel(ExecutionTask task) {
        finish(task);
    }
}
