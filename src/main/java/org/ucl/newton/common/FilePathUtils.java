/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common;

import java.io.File;

/**
 * Instances of this class provide utility functions for working with file
 * paths.
 *
 * @author Blair Butterworth
 */
public class FilePathUtils
{
    public static String appendTrailingSeparator(String path) {
        if (! path.endsWith(File.separator)) {
            return path + File.separator;
        }
        return path;
    }
}
