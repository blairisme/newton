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
 * Instances of this class represent a remote system capable of executing an
 * experiment.
 *
 * @author Blair Butterworth
 */
public interface ExecutionNode
{
    void execute(ExecutionRequest executionRequest);
}
