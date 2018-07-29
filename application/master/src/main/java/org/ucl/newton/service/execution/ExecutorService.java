/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.execution;

import org.ucl.newton.bridge.ExecutionNode;
import org.ucl.newton.framework.Executor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.util.HashMap;
import java.util.Map;

/**
 * Instances of this class manage available {@link ExecutionNode Executors}.
 *
 * @author Blair Butterworth
 */
@Named
public class ExecutorService
{
    private static final int EXECUTOR_CONCURRENT_LIMIT = 2;

    private ExecutorRepository executorRepository;
    private Provider<ExecutionNode> executionNodeFactory;
    private Map<ExecutionNode, Integer> executorUtilization;

    @Inject
    public ExecutorService(ExecutorRepository executorRepository, Provider<ExecutionNode> executionNodeFactory) {
        this.executorRepository = executorRepository;
        this.executionNodeFactory = executionNodeFactory;
        this.executorUtilization = null;
    }

    public boolean isExecutorAvailable() {
        ExecutionNode availableExecutor = getAvailableNode();
        return availableExecutor != null;
    }

    public ExecutionNode reserveExecutor() {
        ExecutionNode executor = getAvailableNode();
        Integer utilization = executorUtilization.get(executor);
        executorUtilization.put(executor, utilization + 1);
        return executor;
    }

    public void releaseExecutor(ExecutionNode executor) {
        Integer utilization = executorUtilization.get(executor);
        executorUtilization.put(executor, utilization - 1);
    }

    private ExecutionNode getAvailableNode() {
        loadExecutors();

        for (Map.Entry<ExecutionNode, Integer> executorUse: executorUtilization.entrySet()){
            if (executorUse.getValue() < EXECUTOR_CONCURRENT_LIMIT) {
                return executorUse.getKey();
            }
        }
        return null;
    }

    private void loadExecutors() {
        if (executorUtilization == null) {
            executorUtilization = new HashMap<>();

            for (Executor executor: executorRepository.getExecutors()){
                ExecutionNode executionNode = executionNodeFactory.get();
                //executionNode.setAddress(executor.getAddress());
                executorUtilization.put(executionNode, 0);
            }
        }
    }
}
