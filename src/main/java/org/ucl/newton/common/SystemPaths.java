/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common;

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
}
