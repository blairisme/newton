/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.application;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Unit tests for the {@link ApplicationStorage} class.
 *
 * @author Blair Butterworth
 */
public class ApplicationStorageTest
{
    @Test
    public void getPathsTest() {
        Path applicationPath = Paths.get("/foo/test");
        ApplicationPreferences preferences = Mockito.mock(ApplicationPreferences.class);
        Mockito.when(preferences.getApplicationPath()).thenReturn(applicationPath);

        ApplicationStorage storage = new ApplicationStorage(preferences);
        Assert.assertEquals(storage.getRootDirectory(), applicationPath);
        Assert.assertEquals(storage.getDataDirectory(), applicationPath.resolve("data"));
        Assert.assertEquals(storage.getProcessorDirectory(), applicationPath.resolve("processors"));
        Assert.assertEquals(storage.getWorkspaceDirectory(), applicationPath.resolve("workspaces"));
        Assert.assertEquals(storage.getTempDirectory(), applicationPath.resolve("temp"));
    }
}