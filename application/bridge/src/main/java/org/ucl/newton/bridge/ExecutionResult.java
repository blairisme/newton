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

import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;

/**
 * Instances of this class contain information about the outcome of an
 * execution request.
 *
 * @author Blair Butterworth
 * @author Ziad Al Halabi
 */
public class ExecutionResult
{
    private String id;
    private String experiment;
    private String version;
    private Date date;
    private Duration duration;
    private URL outputs;

    public ExecutionResult() {
    }

    public ExecutionResult(
        String id,
        String experiment,
        String version,
        Date date,
        Duration duration,
        URL outputs)
    {
        this.id = id;
        this.experiment = experiment;
        this.version = version;
        this.date = date;
        this.duration = duration;
        this.outputs = outputs;
    }

    public String getId() {
        return id;
    }

    public String getExperiment() {
        return experiment;
    }

    public String getVersion() {
        return version;
    }

    public Date getDate() {
        return date;
    }

    public Duration getDuration() {
        return duration;
    }

    public URL getOutputs() {
        return outputs;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        ExecutionResult that = (ExecutionResult)obj;
        return new EqualsBuilder()
            .append(id, that.id)
            .append(experiment, that.experiment)
            .append(version, that.version)
            .append(date, that.date)
            .append(duration, that.duration)
            .append(outputs, that.outputs)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(experiment)
            .append(version)
            .append(date)
            .append(duration)
            .append(outputs)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("experiment", experiment)
            .append("version", version)
            .append("date", date)
            .append("duration", duration)
            .append("outputs", outputs)
            .toString();
    }
}
