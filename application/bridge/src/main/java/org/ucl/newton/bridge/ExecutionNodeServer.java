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
 * Implementors of this interface provide methods that are called when
 * {@link ExecutionNodeClient} methods are invoked.
 *
 * @author Blair Butterworth
 */
public interface ExecutionNodeServer //extends ExecutionNode
{
    void execute(ExecutionRequest executionRequest) throws ExecutionException;
}
