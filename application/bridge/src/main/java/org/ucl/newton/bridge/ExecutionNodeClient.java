/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.bridge;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ucl.newton.common.network.RestRequest;
import org.ucl.newton.common.network.RestServer;
import org.ucl.newton.common.network.UriSchemes;
import org.ucl.newton.common.serialization.JsonSerializer;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
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
    private String host;
    private int port;

    @Autowired
    public ExecutionNodeClient() {
        host = "localhost";
        port = 8080;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
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
    public InputStream getOutput(ExecutionResult executionResult) throws ExecutionException {
        try {
            URL outputUrl = executionResult.getOutputs();
            return outputUrl.openStream();
        }
        catch (Exception cause) {
            throw new ExecutionException(cause);
        }
    }

    private URI getServerAddress() {
        return getServerAddress("");
    }

    private URI getServerAddress(String path) {
        try {
            URIBuilder uriBuilder = new URIBuilder();
            uriBuilder.setScheme(UriSchemes.HTTP);
            uriBuilder.setHost(host);
            uriBuilder.setPort(port);
            uriBuilder.setPath(path);
            return uriBuilder.build();
        }
        catch (URISyntaxException error) {
            throw new ExecutionException(error);
        }
    }

    private RestServer getServer() {
        RestServer restServer = new RestServer();
        restServer.setAddress(getServerAddress());
        restServer.setSerializer(new JsonSerializer());
        restServer.addHeader("Content-Type", "application/json");
        restServer.addHeader("Accept", "application/json");
        return restServer;
    }
}
