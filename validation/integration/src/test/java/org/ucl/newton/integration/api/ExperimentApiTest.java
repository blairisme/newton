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

@Ignore //need test to add experiments before querying for them
public class ExperimentApiTest
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
        Path repositoryPath = tempDirectory.resolve("repository.zip");
        File repositoryFile = repositoryPath.toFile();
        repositoryFile.createNewFile();

        URL repositoryUrl = new URL("http://localhost:9090/api/experiment/experiment-1/repository");

        try (InputStream inputStream = repositoryUrl.openStream();
             OutputStream outputStream = new FileOutputStream(repositoryFile)) {
            IOUtils.copy(inputStream, outputStream);
        }

        Assert.assertTrue(repositoryFile.exists());
        Assert.assertTrue(repositoryFile.length() > 0);
    }
}
