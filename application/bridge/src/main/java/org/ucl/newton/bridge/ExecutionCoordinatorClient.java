/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.bridge;

import org.apache.http.HttpHeaders;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ucl.newton.common.network.*;
import org.ucl.newton.common.serialization.JsonSerializer;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Instances of this class make requests to the remote system controlling
 * execution on {@link ExecutionNode ExecutionNodes}.
 *
 * @author Blair Butterworth
 */
@Component
public class ExecutionCoordinatorClient implements ExecutionCoordinator
{
    private String host;
    private int port;

    @Autowired
    public ExecutionCoordinatorClient() {
        host = "localhost";
        port = 9090;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void executionComplete(ExecutionResult result) {
        try {
            RestServer server = getServer();
            RestRequest request = server.post("api/experiment/complete");
            request.setBody(result, ExecutionResult.class);
            request.make();
        }
        catch (Exception error) {
            throw new ExecutionException(error);
        }
    }

    @Override
    public void executionFailed(ExecutionFailure failure) {
        try {
            RestServer server = getServer();
            RestRequest request = server.post("api/experiment/failed");
            request.setBody(failure, ExecutionFailure.class);
            request.make();
        }
        catch (Exception error) {
            throw new ExecutionException(error);
        }
    }

    @Override
    public InputStream getExperimentRepository(String experimentId) {
        try {
            URI address = getServerAddress("api/experiment/" + experimentId + "/repository");
            BasicHttpConnection connection = getConnection(address);
            return connection.getInputStream();
        }
        catch (Exception cause) {
            throw new ExecutionException(cause);
        }
    }

    @Override
    public InputStream getDataSource(String dataSourceId) {
        try {
            URI address = getServerAddress("api/data/" + dataSourceId);
            BasicHttpConnection connection = getConnection(address);
            return connection.getInputStream();
        }
        catch (Exception cause) {
            throw new ExecutionException(cause);
        }
    }

    @Override
    public InputStream getDataProcessor(String dataProcessorId) {
        try {
            URI address = getServerAddress("api/plugin/processor/" + dataProcessorId);
            BasicHttpConnection connection = getConnection(address);
            return connection.getInputStream();
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
        AuthenticatedRestServer restServer = new AuthenticatedRestServer();
        restServer.setAddress(getServerAddress());
        restServer.setSerializer(new JsonSerializer());
        restServer.addHeader(HttpHeaders.CONTENT_TYPE, MimeTypes.JSON);
        restServer.addHeader(HttpHeaders.ACCEPT, MimeTypes.JSON);
        restServer.setAuthenticationStrategy(new BasicAuthentication("api@newton.com", "password"));
        return restServer;
    }

    private BasicHttpConnection getConnection(URI address) {
        BasicHttpConnection connection = new BasicHttpConnection(address);
        connection.setBasicAuthorization("api@newton.com", "password");
        return connection;
    }
}
