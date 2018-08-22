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
 * given identifier.
 *
 * @author Blair Butterworth
 */
public class NamedSteamProvider implements StreamProvider<String>
{
    private Path rootPath;

    public NamedSteamProvider(Path rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public InputStream getInputStream(String key) throws IOException {
        Path path = rootPath.resolve(key);
        File file = path.toFile();
        if (file.exists()) {
            return new FileInputStream(file);
        }
        return null;
    }

    @Override
    public OutputStream getOutputStream(String key) throws IOException {
        Path path = rootPath.resolve(key);
        File file = path.toFile();
        FileUtils.createNew(file);
        return new FileOutputStream(file);
    }
}
