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
 * Instances of this error are thrown when an error occurs communication
 * between execution nodes.
 *
 * @author Blair Butterworth
 */
public class ExecutionException extends RuntimeException
{
    public ExecutionException(Exception cause) {
        super(cause);
    }
}
