/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.bridge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ucl.newton.common.network.RestRequest;
import org.ucl.newton.common.network.RestServer;
import org.ucl.newton.common.serialization.JsonSerializer;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * Instances of this class make requests to a remote system capable of
 * executing an experiment.
 *
 * @author Blair Butterworth
 */
@Component
public class ExecutionNodeClient implements ExecutionNode
{
    private String address;

    @Autowired
    public ExecutionNodeClient() {
        address = "http://localhost:8080";
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void execute(ExecutionRequest executionRequest) throws ExecutionException {
        try {
            RestServer server = getServer();
            RestRequest request = server.post("api/experiment/execute");
            request.setBody(executionRequest, ExecutionRequest.class);
            request.make();
        }
        catch (Exception cause) {
            throw new ExecutionException(cause);
        }
    }

    @Override
    public void cancel(ExecutionRequest executionRequest) throws ExecutionException {
        try {
            RestServer server = getServer();
            RestRequest request = server.post("api/experiment/cancel");
            request.setBody(executionRequest, ExecutionRequest.class);
            request.make();
        }
        catch (Exception cause) {
            throw new ExecutionException(cause);
        }
    }

    @Override
    public InputStream getExecutionLog(ExecutionResult executionResult) throws ExecutionException {
        try {
            URI logUri = executionResult.getLogPath();
            URL logUrl = logUri.toURL();
            return logUrl.openStream();
        }
        catch (Exception cause) {
            throw new ExecutionException(cause);
        }
    }

    @Override
    public InputStream getExecutionOutput(ExecutionResult executionResult) throws ExecutionException {
        try {
            URI outputUri = executionResult.getDataPath();
            URL outputUrl = outputUri.toURL();
            return outputUrl.openStream();
        }
        catch (Exception cause) {
            throw new ExecutionException(cause);
        }
    }

    @Override
    public InputStream getExecutionVisuals(ExecutionResult executionResult) throws ExecutionException {
        try {
            URI visualsUri = executionResult.getVisualsPath();
            URL visualsUrl = visualsUri.toURL();
            return visualsUrl.openStream();
        }
        catch (Exception cause) {
            throw new ExecutionException(cause);
        }
    }

    private RestServer getServer() {
        RestServer restServer = new RestServer();
        restServer.setAddress(address);
        restServer.setSerializer(new JsonSerializer());
        restServer.addHeader("Content-Type", "application/json");
        restServer.addHeader("Accept", "application/json");
        return restServer;
    }
}
