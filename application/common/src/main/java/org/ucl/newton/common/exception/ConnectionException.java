/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.exception;

import java.io.IOException;

/**
 * Thrown when an error occurs communicating with a remote system.
 *
 * @author Blair Butterworth
 */
public class ConnectionException extends IOException
{
    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(Throwable cause) {
        super("An error occurred communicating with a remote system", cause);
    }
}
