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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ucl.newton.bridge.ExecutionCoordinator;
import org.ucl.newton.bridge.ExecutionResult;
import org.ucl.newton.common.network.RestRequest;
import org.ucl.newton.common.network.RestServer;
import org.ucl.newton.common.serialization.JsonSerializer;

/**
 * Instances of this class make requests to the remote system controlling
 * execution on {@link ExecutionNode ExecutionNodes}.
 *
 * @author Blair Butterworth
 */
@Component
public class ExecutionCoordinatorClient implements ExecutionCoordinator
{
    private String address;

    @Autowired
    public ExecutionCoordinatorClient() {
        //address = "http://51.140.167.6:8080";
        address = "http://localhost:8090";
    }

    public void executionComplete(ExecutionResult executionResult) {
        try {
            RestServer server = getServer();
            RestRequest request = server.post("complete");
            request.setBody(executionResult, ExecutionResult.class);
            request.make();
        }
        catch (Exception e) {
            e.printStackTrace();
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
