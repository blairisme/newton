/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.service;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.newton.bridge.ExecutionCoordinator;
import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.slave.application.ApplicationStorage;
import org.ucl.newton.slave.engine.RequestContext;
import org.ucl.newton.slave.network.MasterServerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Unit tests for the {@link ExperimentService} class.
 *
 * @author Blair Butterworth
 */
public class ExperimentServiceTest extends ServiceTestCase
{
    @Test
    public void addRepositoryTest() throws IOException {
        ApplicationStorage applicationStorage = getApplicationStorage();
        MasterServerFactory serverFactory = getMasterServerFactory();
        ExecutionCoordinator server = serverFactory.get();

        InputStream inputStream = ExperimentServiceTest.class.getResourceAsStream("/experiment-1.zip");
        Mockito.when(server.getExperimentRepository("experiment-1")).thenReturn(inputStream);

        ExecutionRequest request = getExecutionRequest();
        RequestContext context = getRequestContext();

        ExperimentService experimentService = new ExperimentService(applicationStorage, serverFactory);
        experimentService.addRepository(request, context);

        Path workspace = context.getWorkspace().getRepository();
        String[] workspaceContents = workspace.toFile().list();

        Assert.assertNotNull(workspaceContents);
        Assert.assertEquals(2, workspaceContents.length);
    }
}
