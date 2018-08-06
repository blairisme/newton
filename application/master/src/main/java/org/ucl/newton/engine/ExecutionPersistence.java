/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.engine;

import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.ucl.newton.bridge.ExecutionResult;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

/**
 * Persists {@link ExecutionResult ExecutionResults} returned from remote
 * execution of {@link ExecutionTask ExecutionTasks}.
 *
 * @author Blair Butterworth
 */
@Named
public class ExecutionPersistence extends ExecutionPipelineBase
{
    private ExecutionPersistenceHandler handler;
    private Map<ExecutionTask, Future<ExecutionTask>> tasks;

    @Inject
    public ExecutionPersistence(ExecutionPersistenceHandler handler) {
        this.handler = handler;
        this.tasks = new ConcurrentHashMap<>();
    }

    @Override
    public void process(ExecutionTask task) {
        ListenableFuture<ExecutionTask> future = handler.persist(task);
        future.addCallback(new PersistObserver(task, future));
        tasks.put(task, future);
    }

    @Override
    public void cancel(ExecutionTask task) {
        Future<ExecutionTask> future = tasks.get(task);
        if (future != null && ! future.isDone()) {
            future.cancel(false);
        }
    }

    private class PersistObserver implements ListenableFutureCallback<ExecutionTask>
    {
        private ExecutionTask task;
        private Future<ExecutionTask> future;

        public PersistObserver(ExecutionTask task, Future<ExecutionTask> future) {
            this.task = task;
            this.future = future;
        }

        @Override
        public void onSuccess(ExecutionTask newTask) {
            evaluate(newTask);
        }

        @Override
        public void onFailure(Throwable error) {
            task.setError(error);
            evaluate(task);
        }

        private void evaluate(ExecutionTask task) {
            if (! future.isCancelled()) {
                if (task.hasError()) {
                    finish(task);
                } else {
                    proceed(task);
                }
            }
        }
    }
}
