/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.network;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.ucl.newton.bridge.*;
import org.ucl.newton.slave.engine.RequestHandler;

import javax.inject.Named;

/**
 * Instances of this execute experiment computation requests initiated by the
 * Newton system.
 *
 * @author Ziad Halabi
 * @author Blair Butterworth
 */
@Named
@SuppressWarnings("unused")
public class SlaveServer implements ExecutionNodeServer
{
    private RequestHandler requestHandler;
    private ExecutionCoordinator executionCoordinator;

    @Autowired
    public SlaveServer(RequestHandler requestHandler, MasterServerFactory masterServerFactory) {
        this.requestHandler = requestHandler;
        this.executionCoordinator = masterServerFactory.get();
    }

    @Async
    @Override
    public void execute(ExecutionRequest request) throws ExecutionException {
        try {
            ExecutionResult executionResult = requestHandler.process(request);
            executionCoordinator.executionComplete(executionResult);
        }
        catch (Exception error) {
            error.printStackTrace(); //log this instead
            executionCoordinator.executionFailed(error.getMessage());
        }
    }

    @Async
    @Override
    public void cancel(ExecutionRequest executionRequest) throws ExecutionException {
    }
}