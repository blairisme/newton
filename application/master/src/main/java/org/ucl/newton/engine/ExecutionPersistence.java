/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.engine;

import org.ucl.newton.bridge.ExecutionResult;

/**
 * Persists {@link ExecutionResult ExecutionResults} returned from remote
 * execution of {@link ExecutionTask ExecutionTasks}.
 *
 * @author Blair Butterworth
 */
public interface ExecutionPersistence extends ExecutionPipelineElement
{
}
