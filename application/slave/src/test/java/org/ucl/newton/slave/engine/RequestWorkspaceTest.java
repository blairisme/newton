package org.ucl.newton.slave.engine;

import org.junit.Test;
import org.ucl.newton.bridge.ExecutionRequest;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class RequestWorkspaceTest
{
    @Test
    public void getPathsTest() {
        Path applicationPath = Paths.get("/foo/test");
        ExecutionRequest request = new ExecutionRequest("1", "2", "3", "4", "main.py", "*.*", Collections.emptyList());

        RequestWorkspace workspace = new RequestWorkspace(applicationPath, request);

    }
}
