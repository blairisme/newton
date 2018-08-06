/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.bridge;

/**
 * Instances of this class build {@link ExecutionFailure} objects.
 *
 * @author Blair Butterworth
 */
public class ExecutionFailureBuilder
{
    private String id;
    private String experiment;
    private String version;
    private String error;

    public ExecutionFailureBuilder forRequest(ExecutionRequest request) {
        this.id = request.getId();
        this.experiment = request.getExperiment();
        this.version = request.getVersion();
        return this;
    }

    public ExecutionFailureBuilder setException(Throwable exception) {
        this.error = exception.getMessage();
        return this;
    }

    public ExecutionFailure build() {
        return new ExecutionFailure(id, experiment, version, error);
    }
}
