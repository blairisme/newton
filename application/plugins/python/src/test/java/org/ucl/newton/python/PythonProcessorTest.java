/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.python;

import org.apache.commons.io.FileUtils;
import org.junit.*;
import org.ucl.newton.common.file.PathUtils;
import org.ucl.newton.common.file.SystemPaths;
import org.ucl.newton.common.lang.JarClassLoader;
import org.ucl.newton.common.lang.JarInstanceLoader;
import org.ucl.newton.common.process.CommandExecutor;
import org.ucl.newton.common.process.CommandExecutorFactory;
import org.ucl.newton.sdk.plugin.PluginVisualization;
import org.ucl.newton.sdk.processor.DataProcessor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Set;

public class PythonProcessorTest
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
    @Ignore //requires python to be installed
    public void processTest() throws Exception {
        Path workingDirectory = tempDirectory.resolve("output");
        workingDirectory.toFile().mkdirs();

        Path logFile = tempDirectory.resolve("log.txt");
        PathUtils.createNew(logFile);

        URL scriptUrl = getClass().getResource("/main.py");
        File file = new File(scriptUrl.toURI());

        CommandExecutorFactory executorFactory = new CommandExecutorFactory();
        CommandExecutor executor = executorFactory.get();
        executor.redirectError(logFile);
        executor.redirectOutput(logFile);
        executor.workingDirectory(workingDirectory);

        PythonProcessor processor = new PythonProcessor();
        processor.process(executor, file.toPath());

        String commandOutput = FileUtils.readFileToString(logFile.toFile(), StandardCharsets.UTF_8);
        Assert.assertEquals("", commandOutput);

        File[] outputs = workingDirectory.toFile().listFiles();
        Assert.assertEquals(2, outputs.length);
    }

    @Test
    public void visualizationTest() {
        PythonProcessor processor = new PythonProcessor();
        PluginVisualization visualization = processor.getVisualization();
        Assert.assertTrue(! visualization.getName().isEmpty());
        Assert.assertTrue(! visualization.getDescription().isEmpty());
    }

    @Test
    public void discoveryTest() throws Exception {
        URL jarUrl = getClass().getResource("/python.jar");
        JarClassLoader jarClassLoader = new JarClassLoader(jarUrl);
        JarInstanceLoader jarInstanceLoader = new JarInstanceLoader(jarClassLoader);
        Set<DataProcessor> processors = jarInstanceLoader.getImplementors(DataProcessor.class, "org.ucl.newton");
        Assert.assertEquals(1, processors.size());
    }
}
