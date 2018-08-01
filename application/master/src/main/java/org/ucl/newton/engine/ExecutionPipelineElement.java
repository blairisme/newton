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
 * A base {@link ExecutionPipeline} implementation allows pipeline superclasses
 * to control the flow of execution through the pipeline sequence.
 *
 * @author Blair Butterworth
 */
public interface ExecutionPipelineElement extends ExecutionPipeline
{
    public void setNext(ExecutionPipeline next);

    public void setObserver(ExecutionPipelineObserver observer);

    public void proceed(ExecutionTask task);

    public void finish(ExecutionTask task);
}
