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
import org.ucl.newton.common.network.RestRequest;
import org.ucl.newton.common.network.RestServer;
import org.ucl.newton.common.serialization.JsonSerializer;

/**
 * Instances of this class represent a remote system capable of executing an
 * experiment.
 *
 * @author Blair Butterworth
 */
@Component
@Qualifier("client")
public class ExecutionNodeClient implements ExecutionNode
{
    private String address;

    @Autowired
    public ExecutionNodeClient() {
        //address = "http://51.140.167.6:8080";
        address = "http://192.168.1.162:8080";
    }

    public void execute(ExecutionRequest executionRequest) {
        try {
            RestServer server = getServer();
            RestRequest request = server.post("execute");
            request.setBody(executionRequest, ExecutionRequest.class);
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
