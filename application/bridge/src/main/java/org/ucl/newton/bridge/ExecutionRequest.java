/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.bridge;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collection;

/**
 * Instances of this class represent work that can be executed on an
 * {@link ExecutionNode}.
 *
 * @author Blair Butterworth
 */
public class ExecutionRequest
{
    private String id;
    private String experiment;
    private String version;
    private String processor;
    private String script;
    private String output;
    private Collection<String> datasources;

    public ExecutionRequest() {
    }

    public ExecutionRequest(
        String id,
        String experiment,
        String version,
        String processor,
        String script,
        String output,
        Collection<String> datasources)
    {
        this.id = id;
        this.experiment = experiment;
        this.version = version;
        this.processor = processor;
        this.script = script;
        this.output = output;
        this.datasources = datasources;
    }

    public String getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public String getExperiment() {
        return experiment;
    }

    public String getProcessor() {
        return processor;
    }

    public String getScript() {
        return script;
    }

    public String getOutput() {
        return output;
    }

    public Collection<String> getDatasources() {
        return datasources;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object == this) return true;
        if (object.getClass() != getClass()) return false;

        ExecutionRequest other = (ExecutionRequest)object;
        return new EqualsBuilder()
            .append(this.id, other.id)
            .append(this.experiment, other.experiment)
            .append(this.version, other.version)
            .append(this.processor, other.processor)
            .append(this.script, other.script)
            .append(this.output, other.output)
            .append(this.datasources, other.datasources)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(experiment)
            .append(version)
            .append(processor)
            .append(script)
            .append(output)
            .append(datasources)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("experiment", experiment)
            .append("version", version)
            .append("processor", processor)
            .append("script", script)
            .append("output", output)
            .append("datasources", datasources)
            .toString();
    }
}
