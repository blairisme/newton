/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.file;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Instances of this class provide known file system locations.
 *
 * @author Blair Butterworth
 */
public class SystemPaths
{
    public static Path getTempDirectory() {
        String tmpdir = System.getProperty("java.io.tmpdir");
        return Paths.get(tmpdir);
    }

    public static Path getTempDirectory(String name) {
        Path tempPath = SystemPaths.getTempDirectory();
        Path newPath = tempPath.resolve(name);
        newPath.toFile().mkdirs();
        return newPath;
    }

    public static Path getUserDirectory() {
        String userDirectory = System.getProperty("user.home");
        return Paths.get(userDirectory);
    }

}
