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

import javax.inject.Provider;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

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

    public static Collection<Path> unzip(InputStream input, Function<Path, OutputStream> outputFactory) throws IOException {
        Collection<Path> unzippedPaths = new ArrayList<>();

        try (ZipInputStream zipStream = new ZipInputStream(input)) {
            ZipEntry entry = zipStream.getNextEntry();

            while (entry != null) {
                if (!entry.isDirectory()) {
                    Path entryPath = Paths.get(entry.getName());
                    unzippedPaths.add(entryPath);

                    try (OutputStream outputStream = outputFactory.apply(entryPath)) {
                        IOUtils.copy(zipStream, outputStream);
                    }
                }
                zipStream.closeEntry();
                entry = zipStream.getNextEntry();
            }
        }
        return unzippedPaths;
    }

    public static void zip(File[] files, File zipFile) throws IOException {
        try (ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (File file: files) {
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipStream.putNextEntry(zipEntry);

                try (InputStream fileStream = new FileInputStream(file)) {
                    IOUtils.copy(fileStream, zipStream);
                }
                zipStream.closeEntry();
            }
        }
    }
}