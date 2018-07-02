/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.webapp;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.ucl.newton.common.FilePathUtils;
import org.ucl.newton.common.SystemPaths;

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
        return FilePathUtils.appendTrailingSeparator(rootPath.toString());
    }

    public void write(String group, String identifier, InputStream inputStream) throws IOException {
        Path path = getPath(group, identifier);
        File file = path.toFile();
        file.mkdirs();
        file.delete();
        try (OutputStream outputStream = new FileOutputStream(file)) {
            IOUtils.copy(inputStream, outputStream);
        }
    }

    private Path getPath(String group, String identifier) {
        return rootPath.resolve(group).resolve(identifier);
    }
}
