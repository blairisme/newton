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
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.ExperimentBuilder;
import org.ucl.newton.framework.ExperimentVersion;
import org.ucl.newton.framework.ExperimentVersionBuilder;
import org.ucl.newton.service.experiment.ExperimentService;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * Instances of this class store the results of experiment execution.
 *
 * @author Blair Butterworth
 */
@Service
public class ExecutionRepository
{
    private ApplicationStorage applicationStorage;
    private ExperimentService experimentService;

    @Autowired
    public ExecutionRepository(ApplicationStorage applicationStorage, ExperimentService experimentService) {
        this.applicationStorage = applicationStorage;
        this.experimentService = experimentService;
    }

    public void add(ExecutionNode executionNode, ExecutionResult executionResult) {
        Path logPath = persistLog(executionNode, executionResult);
        Path outputPath = persistOutput(executionNode, executionResult);
        persistExperiment(executionResult, logPath, outputPath);
    }

    private Path persistLog(ExecutionNode executionNode, ExecutionResult executionResult) {
        String group = "experiment/" + executionResult.getExperimentId();
        String identifier = "log.txt";

        try(InputStream inputStream = executionNode.getExecutionLog(executionResult);
            OutputStream outputStream = applicationStorage.getOutputStream(group, identifier)) {
            IOUtils.copy(inputStream, outputStream);
            return applicationStorage.getOutputPath(group, identifier);
        }
        catch (Exception error){
            throw new RuntimeException(error); //Replace - bad form
        }
    }

    private Path persistOutput(ExecutionNode executionNode, ExecutionResult executionResult) {
        String group = "experiment/" + executionResult.getExperimentId();
        String identifier = "output.zip";

        try(InputStream inputStream = executionNode.getExecutionOutput(executionResult);
            OutputStream outputStream = applicationStorage.getOutputStream(group, identifier)) {
            IOUtils.copy(inputStream, outputStream);
            return applicationStorage.getOutputPath(group, identifier);
        }
        catch (Exception error){
            throw new RuntimeException(error); //Replace - bad form
        }
    }

    private void persistExperiment(ExecutionResult executionResult, Path logPath, Path outputPath) {
        Experiment experiment = experimentService.getExperimentById(executionResult.getExperimentId());

        ExperimentVersionBuilder versionBuilder = new ExperimentVersionBuilder();
        versionBuilder.forExperiemnt(experiment);
        versionBuilder.setExperimentLog(logPath);
        versionBuilder.setExperimentOutput(outputPath);
        ExperimentVersion version = versionBuilder.build();

        ExperimentBuilder experimentBuilder = new ExperimentBuilder();
        experimentBuilder.copyExperiment(experiment);
        experimentBuilder.addVersion(version);
        Experiment newExperiment = experimentBuilder.build();

        experimentService.update(newExperiment);
    }
}
