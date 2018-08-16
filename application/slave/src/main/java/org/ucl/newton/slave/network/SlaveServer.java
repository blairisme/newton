/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.network;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;
import org.ucl.newton.bridge.*;
import org.ucl.newton.slave.engine.RequestHandler;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Instances of this execute experiment computation requests initiated by the
 * Newton system.
 *
 * @author Ziad Halabi
 * @author Blair Butterworth
 */
@Named
@Singleton
@SuppressWarnings("unused")
public class SlaveServer implements ExecutionNodeServer
{
    private static final Log logger = LogFactory.getLog(SlaveServer.class);

    private RequestHandler requestHandler;
    private ExecutionCoordinator executionCoordinator;
    private Map<ExecutionRequest, Future<ExecutionResult>> requests;

    @Inject
    public SlaveServer(RequestHandler requestHandler, MasterServerFactory masterServerFactory) {
        this.requests = new HashMap<>();
        this.requestHandler = requestHandler;
        this.executionCoordinator = masterServerFactory.get();
    }

    @Override
    @Async("network")
    public void execute(ExecutionRequest request) throws ExecutionException {
        logger.info("Experiment execution requested: " + request);
        try{
            logger.info("*****IP="+InetAddress.getLocalHost().getHostAddress());
        }catch (Exception e){
            logger.error("****error getting ip", e);
        }
        ListenableFuture<ExecutionResult> future = requestHandler.process(request);
        future.addCallback(new ExecutionObserver(this, request));
        requests.put(request, future);
    }

    @Override
    @Async("network")
    public void cancel(ExecutionRequest request) throws ExecutionException {
        Future<ExecutionResult> future = requests.get(request);
        if (future != null && ! future.isCancelled()) {
            logger.info("Experiment execution cancelled: " + request);
            future.cancel(true);
        }
    }

    @Async("network")
    public void executionSuccess(ExecutionRequest request, ExecutionResult result) {
        logger.info("Experiment execution completed: " + result);
        executionCoordinator.executionComplete(result);
    }

    @Async("network")
    public void executionFailure(ExecutionRequest request, Throwable error) {
        logger.error("Experiment execution failed: " + request, error);
        executionCoordinator.executionFailed(new ExecutionFailureBuilder()
            .forRequest(request)
            .setException(error)
            .build());
    }
}