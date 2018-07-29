/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.process;

/**
 * Thrown when errors occur when exucting commands through
 * {@link CommandExecutor CommandExecutors}.
 *
 * @author Blair Butterworth
 */
public class CommandExecutionException extends RuntimeException
{
    public CommandExecutionException(Throwable cause) {
        super(cause);
    }
}
