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
public abstract class ExecutionPipelineElement implements ExecutionPipeline
{
    private ExecutionPipeline next;
    private ExecutionPipelineObserver observer;

    public ExecutionPipelineElement() {
    }

    public void setNext(ExecutionPipeline next) {
        this.next = next;
    }

    public void setObserver(ExecutionPipelineObserver observer) {
        this.observer = observer;
    }

    public void proceed(ExecutionTask task) {
        if (next != null) {
            next.process(task);
        }
    }

    public void halt(ExecutionTask task) {
        if (observer != null) {
            observer.executionComplete(task);
        }
    }
}
