/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.engine;

import org.ucl.newton.bridge.ExecutionRequest;
import org.ucl.newton.framework.Experiment;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

/**
 * Instances of this class build {@link ExecutionRequest} objects.
 *
 * @author Blair Butterworth
 */
public class ExecutionRequestBuilder
{
    private String id;
    private String experiment;
    private String version;
    private String processor;
    private String script;
    private String output;
    private Collection<String> dataSources;

    public ExecutionRequestBuilder() {
        this.id = UUID.randomUUID().toString();
        this.processor = "newton-python";
        this.script = "main.py";
        this.output = "*.csv";
        this.dataSources = Arrays.asList("weather.csv");
    }

    public ExecutionRequestBuilder forExperiment(Experiment experiment) {
        this.experiment = experiment.getIdentifier();
        this.version = Integer.toString(experiment.getVersions().size() + 1);
        return this;
    }

    public ExecutionRequest build() {
        return new ExecutionRequest(id, experiment, version, processor, script, output, dataSources);
    }
}
