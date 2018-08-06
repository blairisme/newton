/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.file;

import java.io.IOException;

/**
 * Represents a function that accepts one argument, produces a result and has
 * the possibility of producing an {@link IOException}.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 *
 * @author Blair Butterworth
 */
public interface IoFunction <T, R>
{
    R get(T value) throws IOException;
}
