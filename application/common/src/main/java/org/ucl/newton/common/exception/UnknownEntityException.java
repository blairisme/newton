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
 * Thrown when a item is requested but doesn't exist.
 *
 * @author Blair Butterworth
 */
public class UnknownEntityException extends RuntimeException
{
    public UnknownEntityException(String message) {
        super(message);
    }
}
