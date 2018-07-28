/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.exception;

/**
 * Thrown if a Newton plugin is incorrectly implemented.
 *
 * @author Blair Butterworth
 */
public class InvalidPluginException extends RuntimeException
{
    public InvalidPluginException(String message) {
        super(message);
    }

    public InvalidPluginException(Throwable cause) {
        super(cause);
    }
}
