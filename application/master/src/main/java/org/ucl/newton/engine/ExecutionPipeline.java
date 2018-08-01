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
 * Implementors of this interface implement a component in the Newton engine
 * pipeline. The pipeline is a set of components, each called in sequence, that
 * perform an operation related to the execution of an experiment.
 *
 * @author Blair Butterworth
 */
public interface ExecutionPipeline
{
    void process(ExecutionTask task);

    void cancel(ExecutionTask task);
}
