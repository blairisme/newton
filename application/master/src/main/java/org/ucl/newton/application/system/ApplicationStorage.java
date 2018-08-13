/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.system;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.ucl.newton.common.file.PathUtils;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Instances of this class provide access to file system resources
 *
 * @author Blair Butterworth
 */
@Service
public class ApplicationStorage
{
    private Path rootPath;

    @Inject
    public ApplicationStorage(ApplicationPreferences applicationPreferences) {
        rootPath = Paths.get(applicationPreferences.getProgramDirectory());
    }

    public String getRootPath() {
        return PathUtils.appendTrailingSeparator(rootPath.toString());
    }

    public Path getApplicationDirectory() {
        return rootPath;
    }

    public Path getTempDirectory() {
        return rootPath.resolve("temp");
    }

    public Path getExperimentDirectory() {
        return rootPath.resolve("experiment");
    }

    public Path getPluginsDirectory() {
        return rootPath.resolve("plugins");
    }

    public Path getPluginConfigDirectory() {
        return getPluginsDirectory().resolve("configuration");
    }

    public Path getProcessorDirectory() {
        return getPluginsDirectory().resolve("processor");
    }

    public Path getDataDirectory() {
        return rootPath.resolve("data");
    }

    public Path getDataDirectory(String child) {
        return getDataDirectory().resolve(child);
    }

    public Path getIndexDirectory() {
        return rootPath.resolve("index");
    }

    public InputStream getInputStream(Path relativePath) throws IOException {
        Path path = rootPath.resolve(relativePath);
        File file = path.toFile();
        return new FileInputStream(file);
    }

    public OutputStream getOutputStream(Path relativePath) throws IOException {
        Path path = rootPath.resolve(relativePath);
        File file = path.toFile();
        FileUtils.forceMkdirParent(file);

        file.delete();
        return new FileOutputStream(file);
    }
}
