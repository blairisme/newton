/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.sdk.processor;

/**
 * Thrown when an error occurs during data processing.
 *
 * @author Blair Butterworth
 * @author Ziad Al Halabi
 */
public class DataProcessorException extends RuntimeException
{
    public DataProcessorException(Throwable cause) {
        super(cause);
    }
}
