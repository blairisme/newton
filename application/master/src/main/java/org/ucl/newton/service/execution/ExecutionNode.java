/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.execution;

import com.google.common.collect.ImmutableMap;
import org.ucl.newton.common.network.RestRequest;
import org.ucl.newton.common.network.RestServer;
import org.ucl.newton.common.serialization.JsonSerializer;

import java.util.UUID;

/**
 * Instances of this class represent a remote system capable of executing an
 * experiment.
 *
 * @author Blair Butterworth
 */
public class ExecutionNode
{
    private String address;

    public ExecutionNode() {
        address = "http://51.140.167.6:8080";
    }

    public void schedule(ExecutionRequest executionRequest) {
        RestServer restServer = getServer();
        RestRequest restRequest = restServer.post("analyse");
        restRequest.setParameters(ImmutableMap.of(
            "id", "12345",
            "mainFilename", "test.py",
            "repoUrl", "https://github.com/ziad-alhalabi/python-test/archive/master.zip",
            "type", "0",
            "outputPattern", "*.py"));

        try {
            restRequest.make();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private RestServer getServer() {
        RestServer restServer = new RestServer();
        restServer.setAddress(address);
        restServer.setSerializer(new JsonSerializer());
        return restServer;
    }
}
