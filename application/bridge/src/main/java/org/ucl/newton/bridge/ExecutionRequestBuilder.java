/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.bridge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Instances of this class build {@link ExecutionRequest ExecutionRequests}.
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
    private Collection<String> datasources;

    public ExecutionRequestBuilder() {
        this.id = UUID.randomUUID().toString();
        this.datasources = new ArrayList<>();
    }

    public ExecutionRequestBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public ExecutionRequestBuilder setExperiment(String experiment) {
        this.experiment = experiment;
        return this;
    }

    public ExecutionRequestBuilder setVersion(String version) {
        this.version = version;
        return this;
    }

    public ExecutionRequestBuilder setProcessor(String processor) {
        this.processor = processor;
        return this;
    }

    public ExecutionRequestBuilder setScript(String script) {
        this.script = script;
        return this;
    }

    public ExecutionRequestBuilder setOutput(String output) {
        this.output = output;
        return this;
    }

    public ExecutionRequestBuilder addDataSource(String dataSource) {
        this.datasources.add(dataSource);
        return this;
    }

    public ExecutionRequestBuilder removeDataSource(String dataSource) {
        this.datasources.remove(dataSource);
        return this;
    }

    public ExecutionRequestBuilder setDataSources(Collection<String> datasources) {
        this.datasources = datasources;
        return this;
    }

    public ExecutionRequest build() {
        return new ExecutionRequest(id, experiment, version, processor, script, output, datasources);
    }
}
