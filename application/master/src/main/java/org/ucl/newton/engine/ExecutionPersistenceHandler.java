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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.bridge.ExecutionNode;
import org.ucl.newton.bridge.ExecutionResult;
import org.ucl.newton.common.archive.ZipUtils;
import org.ucl.newton.common.exception.ConnectionException;
import org.ucl.newton.common.file.IoFunction;
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

/**
 * Asynchronously persists a given {@link ExecutionResult} returned from remote
 * execution of and {@link ExecutionTask}.
 *
 * @author Blair Butterworth
 */
@Named
public class ExecutionPersistenceHandler
{
    private static Logger logger = LoggerFactory.getLogger(ExecutionPersistenceHandler.class);

    private static final String VERSIONS_DIRECTORY = "versions";
    private static final String OUTPUT_FILE_NAME = "output.zip";

    private ExecutionNode executionNode;
    private ExperimentService experimentService;
    private ApplicationStorage applicationStorage;

    @Inject
    public ExecutionPersistenceHandler(
        ApplicationStorage applicationStorage,
        ExperimentService experimentService,
        ExecutionRemoteNode nodeFactory)
    {
        this.executionNode = nodeFactory.get();
        this.experimentService = experimentService;
        this.applicationStorage = applicationStorage;
    }

    @Async
    public ListenableFuture<ExecutionTask> persist(ExecutionTask task) {
        try {
            ExecutionResult executionResult = task.getResult();
            Path destination = getDestination(executionResult);

            Path output = downloadOutput(executionNode, executionResult, destination);
            Collection<Path> outputs = uncompressOutput(output, destination);

            FileUtils.deleteQuietly(output.toFile());
            persistExperiment(executionResult, outputs);
        }
        catch (Throwable error) {
            logger.error("Persistence failed", error);
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
            Collection<Path> contents = ZipUtils.unzip(inputStream, getOutputStreamFactory(destination));
            Path resourcePath = applicationStorage.getApplicationDirectory().relativize(destination);
            return PathUtils.resolve(resourcePath, contents);
        }
    }

    public IoFunction<Path, OutputStream> getOutputStreamFactory(Path relativePath) {
        return (path) -> applicationStorage.getOutputStream(relativePath.resolve(path));
    }

    private void persistExperiment(ExecutionResult executionResult, Collection<Path> outputs) {
        Experiment experiment = experimentService.getExperimentByIdentifier(executionResult.getExperiment());

        ExperimentVersionBuilder versionBuilder = new ExperimentVersionBuilder();
        versionBuilder.forExperiment(experiment);
        versionBuilder.setExperimentOutputs(outputs);
        versionBuilder.setCreated(executionResult.getDate());
        versionBuilder.setDuration(executionResult.getDuration());

        experiment.addVersion(versionBuilder.build());
        experimentService.updateExperiment(experiment);
    }
}
