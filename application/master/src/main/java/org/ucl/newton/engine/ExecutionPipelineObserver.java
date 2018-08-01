/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.engine;

/**
 * Implementors of this interface are notified when a given
 * {@link ExecutionTask} has completed execution.
 *
 * @author Blair Butterworth
 */
public interface ExecutionPipelineObserver
{
    void executionComplete(ExecutionTask task);
}
