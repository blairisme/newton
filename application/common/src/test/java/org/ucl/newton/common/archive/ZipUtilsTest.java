/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.archive;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ucl.newton.common.file.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;

public class ZipUtilsTest
{
    private Path tempDirectory;

    @Before
    public void setup() {
        tempDirectory = SystemUtils.newTempDirectory(getClass().getName());
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(tempDirectory.toFile());
    }

    @Test
    public void zipDirectoryTest() throws IOException {
        Path directory = tempDirectory.resolve("zipDirectoryTest");
        createTestFile(directory, "fileA.txt");
        createTestFile(directory, "fileB.txt");
        createTestFile(directory, "fileC.txt");

        Path zipPath = tempDirectory.resolve("test.zip");
        ZipUtils.zip(directory, zipPath);

        Assert.assertTrue(zipPath.toFile().exists());
        Assert.assertTrue(zipPath.toFile().length() > 0);

        Path unzipDirectory = tempDirectory.resolve("unzip");
        ZipUtils.unzip(zipPath, unzipDirectory);

        File unzipFile = unzipDirectory.toFile();
        Assert.assertTrue(unzipFile.exists());

        Collection<String> unzipContents = Arrays.asList(unzipDirectory.toFile().list());
        Assert.assertEquals(unzipContents.size(), 3);
        Assert.assertTrue(unzipContents.contains("fileA.txt"));
        Assert.assertTrue(unzipContents.contains("fileB.txt"));
        Assert.assertTrue(unzipContents.contains("fileC.txt"));
    }

    private Path createTestFile(Path directory, String name) throws IOException {
        Path result = directory.resolve(name);
        FileUtils.write(result.toFile(), "Lorem ipsum dolor sit amet, consectetur adipiscing elit", StandardCharsets.UTF_8);
        return result;
    }
}
