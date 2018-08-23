/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.engine;

import com.google.common.base.Stopwatch;
import org.ucl.newton.bridge.ExecutionFailure;
import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.bridge.ExecutionResult;
import org.ucl.newton.framework.Experiment;

import java.util.concurrent.TimeUnit;

/**
 * Represents an request to execute an experiment and the results of doing so.
 *
 * @author Blair Butterworth
 */
public class ExecutionTask
{
    private Experiment experiment;
    private ExecutionRequest request;
    private ExecutionResult result;
    private String error;
    private Stopwatch stopwatch;

    public ExecutionTask(Experiment experiment) {
        this.experiment = experiment;
        stopwatch = Stopwatch.createStarted();
    }

    public Experiment getExperiment() {
        return experiment;
    }

    public Stopwatch getStopwatch(){
        return stopwatch;
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

    @Override
    public String toString() {
        return "ExecutionTask{" +
                "experiment=" + request.getExperiment() +
                ", version=" + request.getVersion() +
                ", results=" + (result==null ? "no results" : result.getOutputs()) +
                ", error='" + error + '\'' +
                ", elapsed time=" + stopwatch.elapsed(TimeUnit.MILLISECONDS) +
                '}';
    }
}
