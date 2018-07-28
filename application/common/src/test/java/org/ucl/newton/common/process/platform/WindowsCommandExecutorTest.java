/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.process.platform;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.junit.*;
import org.ucl.newton.common.file.PathUtils;
import org.ucl.newton.common.file.SystemPaths;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;

public class WindowsCommandExecutorTest
{
    private Path tempDirectory;

    @Before
    public void setup() {
        tempDirectory = SystemPaths.getTempDirectory(getClass().getName());
    }

    @After
    public void teardown() throws IOException {
        FileUtils.deleteDirectory(tempDirectory.toFile());
    }

    @Test
    public void executeTest() throws IOException {
        Assume.assumeTrue(SystemUtils.IS_OS_WINDOWS);

        Path logFile = tempDirectory.resolve("log.txt");
        PathUtils.createNew(logFile);

        WindowsCommandExecutor executor = new WindowsCommandExecutor();
        executor.redirectOutput(logFile);
        executor.execute(Arrays.asList("echo", "foo"));

        String commandOutput = FileUtils.readFileToString(logFile.toFile(), StandardCharsets.UTF_8);
        Assert.assertEquals("foo\n", commandOutput);
    }
}
