/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.jupyter;

/**
 * Instances of this exception signify that an error occurred obtaining a URL
 * to the Jupyter server.
 *
 * @author Blair Butterworth
 */
public class JupyterServerException extends RuntimeException
{
    public JupyterServerException(Throwable error) {
        super(error);
    }
}
