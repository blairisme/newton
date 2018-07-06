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
import java.util.List;

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
    private List<ExecutionNode> executionNodes;
    private List<ExecutionRequest> executionRequests;
    private Provider<ExecutionNodeClient> executionNodeFactory;

    @Inject
    public ExecutionService(
        SlaveRepository slaveRepository,
        Provider<ExecutionNodeClient> executionNodeFactory)
    {
        this.slaveRepository = slaveRepository;
        this.executionNodeFactory = executionNodeFactory;
        this.executionRequests = new ArrayList<>();
    }

    public void run(Experiment experiment) {
        ExecutionRequest executionRequest = getExecutionRequest(experiment);
        executionRequests.add(executionRequest);

        List<ExecutionNode> executionNodes = getExecutionNodes();
        executionNodes.get(0).execute(executionRequest);
    }

    @Override
    public void executionComplete(ExecutionResult executionResult) {
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

    private ExecutionRequest getExecutionRequest(Experiment experiment) {
        return new ExecutionRequest(
            "12345",
            "test.py",
            "https://github.com/ziad-alhalabi/python-test/archive/master.zip",
            0,
            "*.py");
    }
}
