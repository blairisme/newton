/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.file;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang3.Validate;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Utility functions for working with {@link File Files}.
 *
 * @author Blair Butterworth
 */
public class FileUtils
{
    public static Collection<File> findChildren(File directory, String pattern) {
        return findChildren(directory, Arrays.asList(pattern));
    }

    public static Collection<File> findChildren(File directory, List<String> patterns) {
        FileFilter fileFilter = new WildcardFileFilter(patterns);
        File[] files = directory.listFiles(fileFilter);
        if (files != null) {
            return Arrays.asList(files);
        }
        return Collections.emptyList();
    }

    public static void createNew(File file) throws IOException {
        Validate.notNull(file);
        File parent = file.getParentFile();

        if (! parent.exists() && ! parent.mkdirs()) {
            throw new IOException("Unable to create containing directory");
        }
        if (! file.exists() && ! file.createNewFile()) {
            throw new IOException("Unable to create new file");
        }
    }
}
