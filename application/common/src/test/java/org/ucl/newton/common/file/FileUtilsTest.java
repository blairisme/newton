/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.file;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Unit tests for the {@link FileUtils} class.
 *
 * @author Blair Butterworth
 */
public class FileUtilsTest
{
    private Path tempDirectory;

    @Before
    public void setup() {
        tempDirectory = SystemPaths.getTempDirectory(getClass().getName());
    }

    @After
    public void tearDown() throws IOException {
        org.apache.commons.io.FileUtils.deleteDirectory(tempDirectory.toFile());
    }

    @Test
    public void findChildrenTest() throws IOException {
        Path file1 = tempDirectory.resolve("foo.txt");
        Path file2 = tempDirectory.resolve("bar.png");
        Path file3 = tempDirectory.resolve("waz.jpg");
        Path file4 = tempDirectory.resolve("test.gif");

        FileUtils.createNew(file1.toFile());
        FileUtils.createNew(file2.toFile());
        FileUtils.createNew(file3.toFile());
        FileUtils.createNew(file4.toFile());

        List<File> expected = Arrays.asList(file1.toFile(), file4.toFile());
        List<File> actual = FileUtils.findChildren(tempDirectory.toFile(), Arrays.asList("*.txt", "test.*"));

        Collections.sort(expected);
        Collections.sort(actual);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void createNewTest() throws IOException {
        Path newPath = tempDirectory.resolve("test1");
        File newFile = newPath.toFile();
        Assert.assertFalse(newFile.exists());

        FileUtils.createNew(newFile);
        Assert.assertTrue(newFile.exists());
    }
}
