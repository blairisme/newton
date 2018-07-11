/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.execution;

import org.springframework.scheduling.annotation.Async;
import org.ucl.newton.bridge.ExecutionCoordinatorServer;
import org.ucl.newton.bridge.ExecutionNode;
import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.bridge.ExecutionResult;
import org.ucl.newton.framework.Experiment;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Instances of this class run experiments.
 *
 * @author Blair Butterworth
 */
@Named
@Singleton
public class ExecutionServiceImpl implements ExecutionService, ExecutionCoordinatorServer
{
    private ExecutorService executorService;
    private ExecutionRepository executionRepository;

    private Queue<Experiment> executionQueue;
    private Map<Integer, ExecutionNode> executionAssignment;
    private Map<Integer, ExecutionRequest> executionRequests;

    @Inject
    public ExecutionServiceImpl(ExecutorService executorService, ExecutionRepository executionRepository)
    {
        this.executorService = executorService;
        this.executionRepository = executionRepository;

        this.executionQueue = new ConcurrentLinkedQueue<>();
        this.executionAssignment = new ConcurrentHashMap<>();
        this.executionRequests = new ConcurrentHashMap<>();
    }

    @Async
    public void beginExecution(Experiment experiment) {
        executionQueue.add(experiment);
        evaluateExecutionQueue();
    }

    @Async
    public void cancelExecution(Experiment experiment) {
        if (executionQueue.contains(experiment)) {
            executionQueue.remove(experiment);
        }
        if (executionAssignment.containsKey(experiment.getId())) {
            ExecutionNode executionNode = executionAssignment.remove(experiment.getId());
            ExecutionRequest executionRequest = executionRequests.remove(experiment.getId());
            executionNode.cancel(executionRequest);
        }
        evaluateExecutionQueue();
    }

    @Async
    public void executionComplete(ExecutionResult executionResult) {
        executionRequests.remove(executionResult.getOwnerId());
        ExecutionNode executionNode = executionAssignment.remove(executionResult.getOwnerId());

        executionRepository.persistResult(executionNode, executionResult);
        executorService.releaseExecutor(executionNode);

        evaluateExecutionQueue();
    }

    public boolean isExecutionComplete(int experimentId) {
        for (Experiment experiment : executionQueue){
            if (Objects.equals(experiment.getId(), experimentId)) {
                return false;
            }
        }
        if (executionAssignment.containsKey(experimentId)) {
            return false;
        }
        return true;
    }

    public boolean isExecutionComplete(Experiment experiment) {
        if (executionQueue.contains(experiment)) {
            return false;
        }
        if (executionAssignment.containsKey(experiment.getId())) {
            return false;
        }
        return true;
    }

    private void evaluateExecutionQueue() {
        while (!executionQueue.isEmpty() && executorService.isExecutorAvailable()) {
            Experiment experiment = executionQueue.remove();

            ExecutionRequestBuilder requestBuilder = new ExecutionRequestBuilder();
            requestBuilder.forExperiment(experiment);

            ExecutionRequest executionRequest = requestBuilder.build();
            ExecutionNode executionNode = executorService.reserveExecutor();

            executionAssignment.put(experiment.getId(), executionNode);
            executionRequests.put(experiment.getId(), executionRequest);

            executionNode.execute(executionRequest);
        }
    }
}
