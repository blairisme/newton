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
import org.junit.*;
import org.ucl.newton.common.file.SystemPaths;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;

@Ignore //need api to wait for data to be obtained before asking for it
public class DataApiTest
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
    public void getProcessorTest() throws Exception{
        Path dataSourcePath = tempDirectory.resolve("data.csv");
        File dataSourceFile = dataSourcePath.toFile();
        dataSourceFile.createNewFile();

        URL dataSourceUrl = new URL("http://localhost:8090/api/data/weather");

        try (InputStream inputStream = dataSourceUrl.openStream();
             OutputStream outputStream = new FileOutputStream(dataSourceFile)) {
            IOUtils.copy(inputStream, outputStream);
        }

        Assert.assertTrue(dataSourceFile.exists());
        Assert.assertTrue(dataSourceFile.length() > 0);
    }
}
