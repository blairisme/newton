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
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A factory for {@link InputStream InputStreams} and
 * {@link OutputStream OutputStreams}.
 *
 * @param <T> a key used to differentiate streams.
 *
 * @author Blair Butterworth
 */
public interface StreamProvider <T>
{
    InputStream getInputStream(T key) throws IOException;

    OutputStream getOutputStream(T key) throws IOException;
}
