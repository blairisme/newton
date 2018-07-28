/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.execution;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.bridge.ExecutionNode;
import org.ucl.newton.bridge.ExecutionResult;
import org.ucl.newton.common.archive.ZipUtils;
import org.ucl.newton.common.exception.ConnectionException;
import org.ucl.newton.common.file.PathUtils;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.ExperimentBuilder;
import org.ucl.newton.framework.ExperimentVersion;
import org.ucl.newton.framework.ExperimentVersionBuilder;
import org.ucl.newton.service.experiment.ExperimentService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;

/**
 * Instances of this class store the results of experiment execution.
 *
 * @author Blair Butterworth
 */
@Service
public class ExecutionRepository
{
    private static final String REPOSITORY_ROOT = "experiment";
    private static final String OUTPUT_FILE_NAME = "output.zip";

    private ApplicationStorage applicationStorage;
    private ExperimentService experimentService;

    @Autowired
    public ExecutionRepository(ApplicationStorage applicationStorage, ExperimentService experimentService) {
        this.applicationStorage = applicationStorage;
        this.experimentService = experimentService;
    }

    public void persistResult(ExecutionNode executionNode, ExecutionResult executionResult) throws IOException {
        Path destination = getDestination(executionResult);
        Path output = downloadOutput(executionNode, executionResult, destination);
        Collection<Path> outputs = uncompressOutput(output, destination);
        persistExperiment(executionResult, outputs);
    }

    private Path getDestination(ExecutionResult executionResult) {
        Path result = Paths.get(REPOSITORY_ROOT);
        result = result.resolve(executionResult.getExperiment());
        result = result.resolve(executionResult.getVersion());
        return result;
    }

    private Path downloadOutput(ExecutionNode executionNode, ExecutionResult executionResult, Path destination) throws ConnectionException {
        Path logPath = destination.resolve(OUTPUT_FILE_NAME);
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

        ExperimentVersion version = versionBuilder.build();
        ExperimentVersion newVersion = experimentService.addVersion(version);

        ExperimentBuilder experimentBuilder = new ExperimentBuilder();
        experimentBuilder.copyExperiment(experiment);
        experimentBuilder.addVersion(newVersion);
        Experiment newExperiment = experimentBuilder.build();

        experimentService.update(newExperiment);
    }
}
