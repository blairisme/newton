/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.api;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ucl.newton.common.file.SystemPaths;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;

public class PluginApiTest
{
    private Path tempDirectory;

    @Before
    public void setup() {
        tempDirectory = SystemPaths.getTempDirectory(getClass().getName());
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(tempDirectory.toFile());
    }

    @Test
    public void getPythonProcessorTest() throws Exception {
        getProcessorTest("newton-python");
    }

    @Test
    public void getJupyterProcessorTest() throws Exception {
        getProcessorTest("newton-jupyter");
    }

    private void getProcessorTest(String pluginId) throws Exception {
        Path processorPath = tempDirectory.resolve("download.jar");
        File processorFile = processorPath.toFile();
        processorFile.createNewFile();


        URL processorUrl = new URL("http://localhost:9090/api/plugin/processor/" + pluginId);

        try (InputStream inputStream = processorUrl.openStream();
             OutputStream outputStream = new FileOutputStream(processorFile)) {
            IOUtils.copy(inputStream, outputStream);
        }

        Assert.assertTrue(processorFile.exists());
        Assert.assertTrue(processorFile.length() > 0);
    }
}
