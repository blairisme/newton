/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.bridge;

/**
 * Instances of this class represent the system coordinating remote execution
 * on {@link ExecutionNode ExecutionNodes}.
 *
 * @author Blair Butterworth
 */
public interface ExecutionCoordinator
{
    void executionComplete(ExecutionResult executionResult);
}
