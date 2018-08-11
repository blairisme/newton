/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.file;

import java.io.*;
import java.nio.file.Path;

/**
 * A {@link StreamProvider} implementation that returns streams based on a
 * given relative path.
 *
 * @author Blair Butterworth
 */
public class PathStreamProvider implements StreamProvider<Path>
{
    private Path rootPath;

    public PathStreamProvider(Path rootPath) {
        this.rootPath = rootPath;
    }

    public InputStream getInputStream(Path relativePath) throws IOException {
        Path path = rootPath.resolve(relativePath);
        File file = path.toFile();
        return new FileInputStream(file);
    }

    public OutputStream getOutputStream(Path relativePath) throws IOException {
        Path path = rootPath.resolve(relativePath);
        File file = path.toFile();
        FileUtils.createNew(file);
        return new FileOutputStream(file);
    }
}
