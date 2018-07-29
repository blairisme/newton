/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.bridge;

import org.apache.commons.lang3.Validate;

import java.net.URL;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

/**
 * Instances of this class build {@link ExecutionResult} objects.
 *
 * @author Blair Butterworth
 */
public class ExecutionResultBuilder
{
    private String id;
    private String experiment;
    private String version;
    private Date date;
    private Duration duration;
    private URL outputs;

    public ExecutionResultBuilder() {
        this.date = new Date();
        this.duration = Duration.ZERO;
    }

    public ExecutionResultBuilder forRequest(ExecutionRequest request) {
        this.id = request.getId();
        this.experiment = request.getExperiment();
        this.version = request.getVersion();
        return this;
    }

    public ExecutionResultBuilder setDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public ExecutionResultBuilder setOutputs(URL outputs) {
        this.outputs = outputs;
        return this;
    }

    public ExecutionResult build() {
        Validate.notNull(id);
        Validate.notNull(experiment);
        Validate.notNull(version);
        Validate.notNull(outputs);
        return new ExecutionResult(id, experiment, version, date, duration, outputs);
    }
}

