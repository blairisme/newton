/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.system;

import org.springframework.stereotype.Service;
import org.ucl.newton.common.file.PathStreamProvider;
import org.ucl.newton.common.file.PathUtils;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    private PathStreamProvider rootStreamProvider;

    @Inject
    public ApplicationStorage(ApplicationPreferences applicationPreferences) {
        rootPath = Paths.get(applicationPreferences.getProgramDirectory());
        rootStreamProvider = new PathStreamProvider(rootPath);
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
        return rootStreamProvider.getInputStream(relativePath);
    }

    public OutputStream getOutputStream(Path relativePath) throws IOException {
        return rootStreamProvider.getOutputStream(relativePath);
    }
}
