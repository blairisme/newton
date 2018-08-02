/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.engine;

import org.ucl.newton.bridge.*;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

/**
 * Executes the given {@link ExecutionTask} on a remote {@link ExecutionNode}.
 *
 * @author Blair Butterworth
 */
@Named
public class ExecutionRemote extends ExecutionPipelineBase implements ExecutionCoordinatorServer
{
    private ExecutionNode executionNode;
    private Map<String, ExecutionTask> executionRequests;

    @Inject
    public ExecutionRemote(ExecutionRemoteNode nodeFactory) {
        this.executionNode = nodeFactory.get();
        this.executionRequests = new HashMap<>();
    }

    //@Async
    @Override
    public void process(ExecutionTask executionTask) {
        try {
            ExecutionRequestFactory requestBuilder = new ExecutionRequestFactory();
            requestBuilder.setExperiment(executionTask.getExperiment());

            ExecutionRequest executionRequest = requestBuilder.build();
            executionTask.setRequest(executionRequest);

            executionNode.execute(executionRequest);
            executionRequests.put(executionRequest.getId(), executionTask);
        }
        catch (Throwable error) {
            executionTask.setError(error);
            finish(executionTask);
        }
    }

    @Override
    public void cancel(ExecutionTask context) {
        ExecutionRequest request = context.getRequest();
        if (request != null) {
            executionRequests.remove(request.getId());
        }
    }

    @Override
    public void executionComplete(ExecutionResult result) {
        ExecutionTask context = executionRequests.remove(result.getId());
        if (context != null) {
            context.setResult(result);
            proceed(context);
        }
    }

    @Override
    public void executionFailed(ExecutionFailure failure) {
        ExecutionTask context = executionRequests.remove(failure.getId());
        if (context != null) {
            context.setError(failure);
            finish(context);
        }
    }
}
