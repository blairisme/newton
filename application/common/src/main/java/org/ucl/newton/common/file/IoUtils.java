/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

/**
 * Utility classes for working with IO classes.
 *
 * @author Blair Butterworth
 */
public class IoUtils
{
    private static Logger logger = LoggerFactory.getLogger(IoUtils.class);

    public static void closeQuitely(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        }
        catch (Exception error) {
            logger.error("Unable to close", error);
        }
    }

    private IoUtils() {
        throw new UnsupportedOperationException();
    }
}
