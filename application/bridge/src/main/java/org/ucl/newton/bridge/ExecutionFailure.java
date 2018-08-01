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

/**
 * Instances of this class contain information about a failure that occurred
 * whilst processing an execution request.
 *
 * @author Blair Butterworth
 */
public class ExecutionFailure
{
    private String id;
    private String experiment;
    private String version;
    private String error;

    public ExecutionFailure() {
    }

    public ExecutionFailure(String id, String experiment, String version, String error) {
        this.id = id;
        this.experiment = experiment;
        this.version = version;
        this.error = error;
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

    public String getError() {
        return error;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        ExecutionFailure that = (ExecutionFailure)obj;
        return new EqualsBuilder()
            .append(id, that.id)
            .append(experiment, that.experiment)
            .append(version, that.version)
            .append(error, that.error)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(experiment)
            .append(version)
            .append(error)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("experiment", experiment)
            .append("version", version)
            .append("error", error)
            .toString();
    }
}
