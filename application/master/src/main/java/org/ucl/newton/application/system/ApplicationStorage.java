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
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.ucl.newton.common.file.PathUtils;

import javax.inject.Inject;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;

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

    public InputStream getInputStream(Path relativePath) throws IOException {
        Path path = rootPath.resolve(relativePath);
        File file = path.toFile();
        return new FileInputStream(file);
    }

    public OutputStream getOutputStream(String group, String identifier) throws IOException {
        return getOutputStream(Paths.get(group, identifier));
    }

    public OutputStream getOutputStream(Path relativePath) throws IOException {
        Path path = rootPath.resolve(relativePath);
        File file = path.toFile();
        FileUtils.forceMkdirParent(file);

        file.delete();
        return new FileOutputStream(file);
    }

    public Function<Path, OutputStream> getOutputStreamFactory(Path relativePath) {
        return (path) -> {
            try {
                return getOutputStream(relativePath.resolve(path));
            }
            catch (IOException e) {
                throw new RuntimeException(e); //bad form...
            }
        };
    }

    public void write(String group, String identifier, InputStream inputStream) throws IOException {
        try (OutputStream outputStream = getOutputStream(group, identifier)) {
            IOUtils.copy(inputStream, outputStream);
        }
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

    public Path getProcessorDirectory() {
        return getPluginsDirectory().resolve("processor");
    }

    public Path getDataDirectory() {
        return rootPath.resolve("data");
    }

    public Path getIndexDirectory() {
        return rootPath.resolve("index");
    }
}
