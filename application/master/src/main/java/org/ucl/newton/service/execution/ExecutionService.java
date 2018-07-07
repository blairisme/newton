/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.execution;

import org.springframework.beans.factory.annotation.Qualifier;
import org.ucl.newton.bridge.*;
import org.ucl.newton.framework.Experiment;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Instances of this class run experiments.
 *
 * @author Blair Butterworth
 */
@Named
@Singleton
public class ExecutionService implements ExecutionCoordinatorServer
{
    private SlaveRepository slaveRepository;
    private ExecutionResultRepository resultRepository;

    private List<ExecutionNode> executionNodes;
    private Map<String, ExecutionNode> executionRequests;
    private Provider<ExecutionNodeClient> executionNodeFactory;

    @Inject
    public ExecutionService(
        SlaveRepository slaveRepository,
        ExecutionResultRepository resultRepository,
        Provider<ExecutionNodeClient> executionNodeFactory)
    {
        this.slaveRepository = slaveRepository;
        this.resultRepository = resultRepository;
        this.executionNodeFactory = executionNodeFactory;
        this.executionRequests = new HashMap<>();
    }

    public void run(Experiment experiment) {
        ExecutionRequest executionRequest = ExecutionRequestBuilder.getExecutionRequest(experiment);
        ExecutionNode executionNode = getAvailableExecutionNode();

        executionRequests.put(executionRequest.getId(), executionNode);
        executionNode.execute(executionRequest);
    }

    @Override
    public void executionComplete(ExecutionResult executionResult) {
        ExecutionNode executionNode = executionRequests.remove(executionResult.getId());
        resultRepository.add(executionNode, executionResult);
    }

    private ExecutionNode getAvailableExecutionNode(){
        List<ExecutionNode> executionNodes = getExecutionNodes();
        return executionNodes.get(0);
    }

    private List<ExecutionNode> getExecutionNodes() {
        if (executionNodes == null) {
            executionNodes = new ArrayList<>();

            for (SlaveDetails slaveDetails: slaveRepository.getSlaveDetails()){
                ExecutionNode executionNode = executionNodeFactory.get();
                executionNodes.add(executionNode);
            }
        }
        return executionNodes;
    }
}
