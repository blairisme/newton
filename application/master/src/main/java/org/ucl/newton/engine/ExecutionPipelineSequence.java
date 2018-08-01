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
 * Creates a new pipeline sequence from the given
 * {@link ExecutionPipelineElement ExecutionPipelineElements}.
 *
 * @author Blair Butterworth
 */
public class ExecutionPipelineSequence
{
    public static ExecutionPipeline from(ExecutionPipelineObserver observer, ExecutionPipelineElement ... elements) {
        if (elements != null && elements.length > 0) {
            ExecutionPipelineElement next = null;

            for (int i = elements.length - 1; i >= 0; --i) {
                ExecutionPipelineElement element = elements[i];
                element.setNext(next);
                element.setObserver(observer);
                next = element;
            }
            return elements[0];
        }
        throw new IllegalArgumentException("Pipeline elements missing");
    }
}
