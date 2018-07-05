/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.network;

/**
 * Instances of this exception are thrown when an unexpected error occurs while
 * communicating with a REST server. The underlying reason for the error can be
 * obtained using the {@link #getCause()} or {@link #getMessage()} methods.
 *
 * @author Blair Butterworth
 */
public class RestException extends Exception
{
    private int statusCode;

    public RestException(Exception cause) {
        super(cause);
    }

    public RestException(int statusCode) {
        super("REST server error: status code " + statusCode);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
