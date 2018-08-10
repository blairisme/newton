/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ucl.newton.framework.Experiment;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Starts and stops {@link Experiment} execution.
 *
 * @author Blair Butterworth
 */
@Named
@Singleton
public class ExecutionEngine implements ExecutionController, ExecutionPipelineObserver
{
    private static Logger logger = LoggerFactory.getLogger(ExecutionEngine.class);

    private ExecutionPipeline pipeline;
    private ExecutionTrigger trigger;
    private Map<String, ExecutionTask> tasks;

    @Inject
    public ExecutionEngine(
        ExecutionRemote executeRemotely,
        ExecutionPersistence persistResults,
        ExecutionPublication publishResults,
        ExecutionDataObserver dataObserver)
    {
        tasks = new ConcurrentHashMap<>();
        trigger = dataObserver;
        trigger.setController(this);
        pipeline = ExecutionPipelineSequence.from(
            this,
            executeRemotely,
            persistResults,
            publishResults);
    }

    @Override
    public void startExecution(Experiment experiment) {
        logger.info("Experiment started", experiment);
        ExecutionTask task = new ExecutionTask(experiment);
        tasks.put(experiment.getIdentifier(), task);
        pipeline.process(task);
    }

    @Override
    public void stopExecution(Experiment experiment) {
        logger.info("Experiment stopped", experiment);
        ExecutionTask task = tasks.get(experiment.getIdentifier());
        if (task != null) {
            pipeline.cancel(task);
        }
    }

    @Override
    public boolean isExecutionComplete(Experiment experiment) {
        return !tasks.containsKey(experiment.getIdentifier());
    }

    @Override
    public void executionComplete(ExecutionTask task) {
        logger.info("Experiment complete", task);
        tasks.remove(task.getExperiment().getIdentifier());

        if (task.hasError()) {
            logger.error(task.getError());
        }
    }
}
