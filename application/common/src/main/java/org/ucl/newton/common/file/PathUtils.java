/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.file;

import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility functions to assist work with {@link Path Paths}.
 *
 * @author Blair Butterworth
 */
public class PathUtils
{
    private PathUtils() {
        throw new UnsupportedOperationException();
    }

    public static String appendTrailingSeparator(String path) {
        if (! path.endsWith(File.separator)) {
            return path + File.separator;
        }
        return path;
    }

    public static void createNew(Path path) throws IOException {
        FileUtils.createNew(path.toFile());
    }

    public static String getName(Path path) {
        return path.getFileName().toString();
    }

    public static Collection<String> getNames(Collection<Path> paths) {
        Collection<String> result = new ArrayList<>();
        for (Path path: paths) {
            result.add(getName(path));
        }
        return result;
    }

    public static Collection<Path> findChildren(Path directory, List<String> patterns) {
        Collection<File> files = FileUtils.findChildren(directory.toFile(), patterns);
        Collection<Path> result = new ArrayList<>(files.size());
        for (File file: files) {
            result.add(file.toPath());
        }
        return result;
    }

    public static Collection<Path> resolve(Path root, Collection<Path> children) {
        Collection<Path> result = new ArrayList<>();
        for (Path child: children) {
            result.add(root.resolve(child));
        }
        return result;
    }

    public static Collection<File> toFile(Collection<Path> paths) {
        Collection<File> result = new ArrayList<>();
        for (Path path: paths) {
            result.add(path.toFile());
        }
        return result;
    }

    public static URL toUrl(Path path) {
        Validate.notNull(path);
        try {
            return path.toUri().toURL();
        }
        catch (MalformedURLException error) {
            throw new IllegalArgumentException(error);
        }
    }

    public static Path getConfigurationPath(){
        return Paths.get("configuration");
    }
}
