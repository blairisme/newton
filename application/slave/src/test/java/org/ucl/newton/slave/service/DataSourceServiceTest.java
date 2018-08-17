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

import java.io.InputStream;
import java.nio.file.Path;

/**
 * Unit tests for the {@link DataSourceService} class.
 *
 * @author Blair Butterworth
 */
public class DataSourceServiceTest extends ServiceTestCase
{
    @Test
    public void addDataSourcesTest() throws Exception {
        ApplicationStorage applicationStorage = getApplicationStorage();
        MasterServerFactory serverFactory = getMasterServerFactory();
        ExecutionCoordinator server = serverFactory.get();

        InputStream inputStream = DataSourceServiceTest.class.getResourceAsStream("/weather.csv");
        Mockito.when(server.getDataSource("weather")).thenReturn(inputStream);

        ExecutionRequest request = getExecutionRequest();
        RequestContext context = getRequestContext();

        DataSourceService dataSourceService = new DataSourceService(applicationStorage, serverFactory);
        dataSourceService.addDataSources(request, context);

        Path dataDirectory = applicationStorage.getDataDirectory();
        String[] dataDirectoryContents = dataDirectory.toFile().list();

        Assert.assertNotNull(dataDirectoryContents);
        Assert.assertEquals(1, dataDirectoryContents.length);
    }
}
