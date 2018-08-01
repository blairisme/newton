/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.engine;

import org.ucl.newton.bridge.ExecutionFailure;
import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.bridge.ExecutionResult;
import org.ucl.newton.framework.Experiment;

/**
 * Represents an request to execute an experiment and the results of doing so.
 *
 * @author Blair Butterworth
 */
public class ExecutionTask
{
    private String identifier;
    private Experiment experiment;
    private ExecutionRequest request;
    private ExecutionResult result;
    private String error;

    public ExecutionTask(Experiment experiment) {
        this.identifier = experiment.getIdentifier();
    }

    public String getIdentifier() {
        return identifier;
    }

    public Experiment getExperiment() {
        return experiment;
    }

    public void setExperiment(Experiment experiment) {
        this.experiment = experiment;
    }

    public ExecutionRequest getRequest() {
        return request;
    }

    public void setRequest(ExecutionRequest request) {
        this.request = request;
    }

    public ExecutionResult getResult() {
        return result;
    }

    public void setResult(ExecutionResult result) {
        this.result = result;
    }

    public boolean hasError() {
        return error != null;
    }

    public String getError() {
        return error;
    }

    public void setError(ExecutionFailure failure) {
        this.error = failure.getError();
    }

    public void setError(Throwable exception) {
        this.error = exception.getMessage();
    }
}
