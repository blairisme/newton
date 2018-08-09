/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.file;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;

/**
 * Unit tests for the {@link SystemPaths} class.
 *
 * @author Blair Butterworth
 */
public class SystemPathsTest
{
    @Test
    public void getUserDirectoryTest() {
        Path userDir = SystemPaths.getUserDirectory();
        Assert.assertNotNull(userDir);
        Assert.assertTrue(userDir.toFile().exists());
    }

    @Test
    public void getTempDirTest() {
        Path tempDir = SystemPaths.getTempDirectory();
        Assert.assertNotNull(tempDir);
        Assert.assertTrue(tempDir.toFile().exists());
    }

    @Test
    public void getTempSubDirTest() {
        Path tempDir = SystemPaths.getTempDirectory();
        Path subDir = SystemPaths.getTempDirectory("test");
        Assert.assertNotNull(subDir);
        Assert.assertEquals(new File(tempDir.toFile(), "test"), subDir.toFile());
    }
}
