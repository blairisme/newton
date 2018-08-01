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
 * Implementors of this interface start the execution of experiments.
 *
 * @author Blair Butterworth
 */
public interface ExecutionTrigger
{
    void setController(ExecutionController controller);
}
