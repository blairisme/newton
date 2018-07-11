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
import org.ucl.newton.common.file.PathUtils;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.ExperimentBuilder;
import org.ucl.newton.framework.ExperimentVersion;
import org.ucl.newton.framework.ExperimentVersionBuilder;
import org.ucl.newton.service.experiment.ExperimentService;

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
    private static final String LOG_FILE_NAME = "log.txt";
    private static final String DATA_DIR_NAME = "data";
    private static final String DATA_FILE_NAME = "data.zip";
    private static final String VISUAL_DIR_NAME = "visual";
    private static final String VISUAL_FILE_NAME = "visual.zip";

    private ApplicationStorage applicationStorage;
    private ExperimentService experimentService;

    @Autowired
    public ExecutionRepository(ApplicationStorage applicationStorage, ExperimentService experimentService) {
        this.applicationStorage = applicationStorage;
        this.experimentService = experimentService;
    }

    public void persistResult(ExecutionNode executionNode, ExecutionResult executionResult) {
        Path destination = getDestination(executionResult);

        Path logPath = downloadLog(executionNode, executionResult, destination);
        Path dataPath = downloadData(executionNode, executionResult, destination);
        Path visualPath = downloadVisual(executionNode, executionResult, destination);

        Collection<Path> dataPaths = uncompressData(dataPath, destination);
        Collection<Path> visualPaths = uncompressVisuals(visualPath, destination);

        persistExperiment(executionResult, logPath, dataPaths, visualPaths);
    }

    private Path getDestination(ExecutionResult executionResult) {
        Path result = Paths.get(REPOSITORY_ROOT);
        result = result.resolve(Integer.toString(executionResult.getOwnerId()));
        result = result.resolve(Integer.toString(executionResult.getOwnerVersion()));
        return result;
    }

    private Path downloadLog(ExecutionNode executionNode, ExecutionResult executionResult, Path destination) {
        Path logPath = destination.resolve(LOG_FILE_NAME);

        try(InputStream inputStream = executionNode.getExecutionLog(executionResult);
            OutputStream outputStream = applicationStorage.getOutputStream(logPath)) {
            IOUtils.copy(inputStream, outputStream);
        }
        catch (Exception error){
            throw new RuntimeException(error); //Replace - bad form
        }
        return logPath;
    }

    private Path downloadData(ExecutionNode executionNode, ExecutionResult executionResult, Path destination)  {
        Path outputPath = destination.resolve(DATA_FILE_NAME);

        try(InputStream inputStream = executionNode.getExecutionOutput(executionResult);
            OutputStream outputStream = applicationStorage.getOutputStream(outputPath)) {
            IOUtils.copy(inputStream, outputStream);
        }
        catch (Exception error){
            throw new RuntimeException(error); //Replace - bad form
        }
        return outputPath;
    }

    private Path downloadVisual(ExecutionNode executionNode, ExecutionResult executionResult, Path destination)  {
        Path visualPath = destination.resolve(VISUAL_FILE_NAME);

        try(InputStream inputStream = executionNode.getExecutionVisuals(executionResult);
            OutputStream outputStream = applicationStorage.getOutputStream(visualPath)) {
            IOUtils.copy(inputStream, outputStream);
        }
        catch (Exception error){
            throw new RuntimeException(error); //Replace - bad form
        }
        return visualPath;
    }

    private Collection<Path> uncompressData(Path archive, Path destination) {
        Path outputPath = destination.resolve(DATA_DIR_NAME);

        try(InputStream inputStream = applicationStorage.getInputStream(archive)) {
            Collection<Path> contents = ZipUtils.unzip(inputStream, applicationStorage.getOutputStreamFactory(outputPath));
            return PathUtils.resolve(outputPath, contents);
        }
        catch (Exception error){
            throw new RuntimeException(error); //Replace - bad form
        }
    }

    private Collection<Path> uncompressVisuals(Path archive, Path destination) {
        Path visualPath = destination.resolve(VISUAL_DIR_NAME);

        try(InputStream inputStream = applicationStorage.getInputStream(archive)) {
            Collection<Path> contents = ZipUtils.unzip(inputStream, applicationStorage.getOutputStreamFactory(visualPath));
            return PathUtils.resolve(visualPath, contents);
        }
        catch (Exception error){
            throw new RuntimeException(error); //Replace - bad form
        }
    }

    private void persistExperiment(ExecutionResult executionResult, Path log, Collection<Path> data, Collection<Path> visuals) {
        Experiment experiment = experimentService.getExperimentById(executionResult.getOwnerId());

        ExperimentVersionBuilder versionBuilder = new ExperimentVersionBuilder();
        versionBuilder.forExperiment(experiment);
        versionBuilder.setExperimentLog(log);
        versionBuilder.setExperimentData(data);
        versionBuilder.setExperimentVisuals(visuals);

        ExperimentVersion version = versionBuilder.build();
        ExperimentVersion newVersion = experimentService.addVersion(version);

        ExperimentBuilder experimentBuilder = new ExperimentBuilder();
        experimentBuilder.copyExperiment(experiment);
        experimentBuilder.addVersion(newVersion);
        Experiment newExperiment = experimentBuilder.build();

        experimentService.update(newExperiment);
    }
}
