/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.archive;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Provides utility functions for working with zip files.
 *
 * @author Blair Butterworth
 */
public class ZipUtils
{
    public static void unzip(File zipFile, File directory) throws IOException {
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try (ZipInputStream zipStream = new ZipInputStream(new FileInputStream(zipFile))) {
            ZipEntry entry = zipStream.getNextEntry();

            while (entry != null) {
                File entryFile = new File(directory, entry.getName());

                if (!entry.isDirectory()) {
                    OutputStream outputStream = new FileOutputStream(entryFile);
                    IOUtils.copy(zipStream, outputStream);
                }
                else {
                    entryFile.mkdirs();
                }
                zipStream.closeEntry();
                entry = zipStream.getNextEntry();
            }
        }
    }
}