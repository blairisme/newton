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
import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.common.file.IoFunction;
import org.ucl.newton.common.file.PathUtils;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;
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
    private ZipUtils() {
        throw new UnsupportedOperationException();
    }

    public static void unzip(Path zipPath, Path destinationPath) throws IOException {
        unzip(zipPath.toFile(), destinationPath.toFile());
    }

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
                    outputStream.close();
                }
                else {
                    entryFile.mkdirs();
                }
                zipStream.closeEntry();
                entry = zipStream.getNextEntry();
            }
        }
    }

    public static Collection<Path> unzip(InputStream input, IoFunction<Path, OutputStream> outputFactory) throws IOException {
        Collection<Path> unzippedPaths = new ArrayList<>();

        try (ZipInputStream zipStream = new ZipInputStream(input)) {
            ZipEntry entry = zipStream.getNextEntry();

            while (entry != null) {
                if (!entry.isDirectory()) {
                    Path entryPath = Paths.get(entry.getName());
                    unzippedPaths.add(entryPath);

                    try (OutputStream outputStream = outputFactory.get(entryPath)) {
                        IOUtils.copy(zipStream, outputStream);
                    }
                }
                zipStream.closeEntry();
                entry = zipStream.getNextEntry();
            }
        }
        return unzippedPaths;
    }

    public static void zip(File directory, File zipFile) throws IOException {
        zip(directory.toPath(), zipFile.toPath());
    }

    public static void zip(Path directory, Path zipFile) throws IOException {
        PathUtils.createNew(zipFile);

        try (ZipOutputStream zipStream = new ZipOutputStream(new FileOutputStream(zipFile.toFile()))) {
            Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    zipStream.putNextEntry(new ZipEntry(directory.relativize(file).toString()));
                    Files.copy(file, zipStream);
                    zipStream.closeEntry();
                    return FileVisitResult.CONTINUE;
                }

                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    zipStream.putNextEntry(new ZipEntry(directory.relativize(dir).toString() + "/"));
                    zipStream.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }

    public static void zip(Collection<Path> files, Path zipFile) throws IOException {
        zip(PathUtils.toFile(files), zipFile.toFile());
    }

    public static void zip(Collection<File> files, File zipFile) throws IOException {
       FileUtils.createNew(zipFile);

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