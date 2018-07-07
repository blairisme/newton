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
import org.ucl.newton.application.webapp.ApplicationStorage;
import org.ucl.newton.bridge.ExecutionNode;
import org.ucl.newton.bridge.ExecutionResult;
import org.ucl.newton.service.experiment.ExperimentService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;

/**
 * Instances of this class persist
 *
 * @author Blair Butterworth
 */
@Named
public class ExecutionResultRepository
{
    private ApplicationStorage applicationStorage;
    private ExperimentService experimentService;

    @Inject
    public ExecutionResultRepository(ApplicationStorage applicationStorage, ExperimentService experimentService) {
        this.applicationStorage = applicationStorage;
        this.experimentService = experimentService;
    }

    public void add(ExecutionNode executionNode, ExecutionResult executionResult) {
        String experimentId = executionResult.getId();
        persistLog(executionNode, experimentId);
        persistOutput(executionNode, experimentId);
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
}
