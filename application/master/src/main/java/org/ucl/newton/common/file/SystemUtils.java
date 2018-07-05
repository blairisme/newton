/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.file;

import java.io.File;
import java.nio.file.Path;

/**
 * Instances of this class provide utility functions for working with the
 * operating system.
 *
 * @author Blair Butterworth
 */
public class SystemUtils
{
    public static Path newTempDirectory(String name) {
        Path tempPath = SystemPaths.getTempDirectory();
        Path newPath = tempPath.resolve(name);

        File newDirectory = newPath.toFile();
        newDirectory.mkdirs();

        return newPath;
    }
}
