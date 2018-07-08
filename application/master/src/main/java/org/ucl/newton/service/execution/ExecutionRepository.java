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
import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.service.experiment.ExperimentService;

import java.io.InputStream;
import java.io.OutputStream;

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

    public void add(ExecutionNode executionNode, ExecutionRequest executionRequest) {
        String experimentId = Integer.toString(executionRequest.getExperimentId());
        persistLog(executionNode, experimentId);
        persistOutput(executionNode, experimentId);
        persistExperiment(executionRequest);
    }

    private void persistLog(ExecutionNode executionNode, String experimentId) {
        try(InputStream inputStream = executionNode.getExecutionLog(experimentId);
            OutputStream outputStream = applicationStorage.getOutputStream("experiment/" + experimentId, "log.txt")) {
            IOUtils.copy(inputStream, outputStream);
        }
        catch (Exception error){
            error.printStackTrace();
        }
    }

    private void persistOutput(ExecutionNode executionNode, String experimentId) {
        try(InputStream inputStream = executionNode.getExecutionOutput(experimentId);
            OutputStream outputStream = applicationStorage.getOutputStream("experiment/" + experimentId, "output.zip")) {
            IOUtils.copy(inputStream, outputStream);
        }
        catch (Exception error){
            error.printStackTrace();
        }
    }

    private void persistExperiment(ExecutionRequest executionRequest) {
        Experiment experiment = experimentService.getExperimentById(executionRequest.getExperimentId());



    }
}
