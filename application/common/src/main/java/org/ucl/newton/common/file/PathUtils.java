package org.ucl.newton.common.file;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

public class PathUtils
{
    public static Collection<Path> getChildren(Path path) {
        Collection<Path> result = new ArrayList<>();
        for (File file: path.toFile().listFiles()) {
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
}
