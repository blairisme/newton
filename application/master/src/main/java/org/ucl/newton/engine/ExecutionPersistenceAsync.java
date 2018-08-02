/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.engine;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.bridge.ExecutionNode;
import org.ucl.newton.bridge.ExecutionResult;
import org.ucl.newton.common.archive.ZipUtils;
import org.ucl.newton.common.exception.ConnectionException;
import org.ucl.newton.common.file.PathUtils;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.ExperimentVersionBuilder;
import org.ucl.newton.service.experiment.ExperimentService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * Persists {@link ExecutionResult ExecutionResults} returned from remote
 * execution of {@link ExecutionTask ExecutionTasks}.
 *
 * @author Blair Butterworth
 */
@Named
public class ExecutionPersistenceAsync extends ExecutionPipelineBase implements ExecutionPersistence
{
    private static final String VERSIONS_DIRECTORY = "versions";
    private static final String OUTPUT_FILE_NAME = "output.zip";

    private ExecutionNode executionNode;
    private ExperimentService experimentService;
    private ApplicationStorage applicationStorage;
    private Map<ExecutionTask, Future<ExecutionTask>> tasks;

    @Inject
    public ExecutionPersistenceAsync(
        ApplicationStorage applicationStorage,
        ExperimentService experimentService,
        ExecutionRemoteNode nodeFactory)
    {
        this.executionNode = nodeFactory.get();
        this.experimentService = experimentService;
        this.applicationStorage = applicationStorage;
        this.tasks = new HashMap<>();
    }

    @Override
    public void process(ExecutionTask task) {
        ListenableFuture<ExecutionTask> future = persist(task);
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

    @Async
    public AsyncResult<ExecutionTask> persist(ExecutionTask task) {
        try {
            ExecutionResult executionResult = task.getResult();
            Path destination = getDestination(executionResult);
            Path output = downloadOutput(executionNode, executionResult, destination);
            Collection<Path> outputs = uncompressOutput(output, destination);
            FileUtils.deleteQuietly(output.toFile());
            persistExperiment(executionResult, outputs);
        }
        catch (Throwable error) {
            task.setError(error);
        }
        return new AsyncResult<>(task);
    }

    private Path getDestination(ExecutionResult executionResult) {
        Path result = applicationStorage.getExperimentDirectory();
        result = result.resolve(executionResult.getExperiment());
        result = result.resolve(VERSIONS_DIRECTORY);
        result = result.resolve(executionResult.getVersion());
        return result;
    }

    private Path downloadOutput(ExecutionNode executionNode, ExecutionResult executionResult, Path destination) throws IOException {
        Path logPath = destination.resolve(OUTPUT_FILE_NAME);
        PathUtils.createNew(logPath);
        try(InputStream inputStream = executionNode.getOutput(executionResult);
            OutputStream outputStream = applicationStorage.getOutputStream(logPath)) {
            IOUtils.copy(inputStream, outputStream);
        }
        catch (Exception cause){
            throw new ConnectionException(cause);
        }
        return logPath;
    }

    private Collection<Path> uncompressOutput(Path archive, Path destination) throws IOException  {
        try(InputStream inputStream = applicationStorage.getInputStream(archive)) {
            Collection<Path> contents = ZipUtils.unzip(inputStream, applicationStorage.getOutputStreamFactory(destination));
            return PathUtils.resolve(destination, contents);
        }
    }

    private void persistExperiment(ExecutionResult executionResult, Collection<Path> outputs) {
        Experiment experiment = experimentService.getExperimentByIdentifier(executionResult.getExperiment());

        ExperimentVersionBuilder versionBuilder = new ExperimentVersionBuilder();
        versionBuilder.forExperiment(experiment);
        versionBuilder.setExperimentOutputs(outputs);
        experiment.addVersion(versionBuilder.build());
        experimentService.update(experiment);
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
