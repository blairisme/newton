/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.service;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.bridge.ExecutionResult;
import org.ucl.newton.common.archive.ZipUtils;
import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.slave.application.ApplicationPreferences;
import org.ucl.newton.slave.application.ApplicationStorage;
import org.ucl.newton.slave.application.ApplicationUrls;
import org.ucl.newton.slave.engine.RequestContext;
import org.ucl.newton.slave.engine.RequestWorkspace;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * Unit tests for the {@link ExperimentService} class.
 *
 * @author Blair Butterworth
 */
public class WorkspaceServiceTest extends ServiceTestCase
{
    @Test
    public void createWorkspaceTest() {
        Assume.assumeFalse("Only applies when run on Unix based systems", SystemUtils.IS_OS_WINDOWS);
        ApplicationUrls applicationUrls = getApplicationUrls();
        ApplicationStorage applicationStorage = getApplicationStorage();
        ApplicationPreferences applicationPreferences = getApplicationPreferences();

        ExecutionRequest request = getExecutionRequest();
        WorkspaceService workspaceService = new WorkspaceService(applicationUrls, applicationStorage, applicationPreferences);

        RequestContext context = workspaceService.createWorkspace(request);
        Assert.assertNotNull(context.getWorkspace());
        Assert.assertNotNull(context.getLogger());
    }

    @Test
    public void collateResultsTest() throws Exception {
        Assume.assumeFalse("Only applies when run on Unix based systems", SystemUtils.IS_OS_WINDOWS);
        ApplicationUrls applicationUrls = getApplicationUrls();
        ApplicationStorage applicationStorage = getApplicationStorage();
        ApplicationPreferences applicationPreferences = getApplicationPreferences();

        ExecutionRequest request = getExecutionRequest();
        RequestContext context = getRequestContext();
        populateWorkspace(context.getWorkspace());

        WorkspaceService workspaceService = new WorkspaceService(applicationUrls, applicationStorage, applicationPreferences);
        ExecutionResult result = workspaceService.collateResults(request, context);

        Assert.assertNotNull(result.getOutputs());

        File archive = context.getWorkspace().getOutput().toFile();
        File archiveContents = tempDirectory.resolve("outputs").toFile();

        ZipUtils.unzip(archive, archiveContents);
        String[] outputs = archiveContents.list();

        Assert.assertNotNull(outputs);
        Arrays.sort(outputs);

        Assert.assertEquals(3, outputs.length);
        Assert.assertEquals("foo.csv", outputs[0]);
        Assert.assertEquals("log.txt", outputs[1]);
        Assert.assertEquals("test.csv", outputs[2]);
    }

    private void populateWorkspace(RequestWorkspace workspace) throws IOException{
        Path workspaceRoot = workspace.getRoot();
        Path logFile = workspace.getLog();
        Path file1 = workspaceRoot.resolve("foo.csv");
        Path file2 = workspaceRoot.resolve("bar.text");
        Path file3 = workspaceRoot.resolve("waz.jpg");
        Path file4 = workspaceRoot.resolve("test.csv");

        FileUtils.createNew(logFile.toFile());
        FileUtils.createNew(file1.toFile());
        FileUtils.createNew(file2.toFile());
        FileUtils.createNew(file3.toFile());
        FileUtils.createNew(file4.toFile());
    }
}
