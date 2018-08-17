package org.ucl.newton.slave.service;

import com.google.common.base.Stopwatch;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;
import org.ucl.newton.bridge.ExecutionCoordinator;
import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.common.file.SystemPaths;
import org.ucl.newton.slave.application.ApplicationStorage;
import org.ucl.newton.slave.engine.RequestContext;
import org.ucl.newton.slave.engine.RequestLogger;
import org.ucl.newton.slave.engine.RequestWorkspace;
import org.ucl.newton.slave.network.MasterServerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

public class ServiceTestCase
{
    protected Path tempDirectory;

    @Before
    public void setup() {
        tempDirectory = SystemPaths.getTempDirectory(getClass().getName());
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(tempDirectory.toFile());
    }

    protected ApplicationStorage getApplicationStorage() {
        return new ApplicationStorage(tempDirectory);
    }

    protected ExecutionCoordinator getExecutionCoordinator() {
        ExecutionCoordinator executionCoordinator = Mockito.mock(ExecutionCoordinator.class);
        return executionCoordinator;
    }

    protected MasterServerFactory getMasterServerFactory() {
        MasterServerFactory masterServerFactory = Mockito.mock(MasterServerFactory.class);
        Mockito.when(masterServerFactory.get()).thenReturn(getExecutionCoordinator());
        return masterServerFactory;
    }

    protected RequestContext getRequestContext() {
        return new RequestContext(getRequestWorkspace(), getRequestLogger(), Stopwatch.createUnstarted());
    }

    protected RequestLogger getRequestLogger() {
        return Mockito.mock(RequestLogger.class);
    }

    protected RequestWorkspace getRequestWorkspace() {
        ApplicationStorage applicationStorage = getApplicationStorage();
        return new RequestWorkspace(applicationStorage.getWorkspaceDirectory(), getExecutionRequest());
    }

    protected ExecutionRequest getExecutionRequest() {
        return new ExecutionRequest("1", "experiment-1", "1", "newton-python", "main.py", "*.csv", Arrays.asList("weather"));
    }
}
