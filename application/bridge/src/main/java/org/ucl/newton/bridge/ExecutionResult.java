/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.bridge;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.net.URI;

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
    private int experimentId;
    private int experimentVersion;
    private URI logPath;
    private URI outputPath;
    private String errorMessage;

    public ExecutionResult() {
    }

    public ExecutionResult(String id, int experimentId, int experimentVersion, URI logPath, URI outputPath, String errorMessage) {
        this.id = id;
        this.experimentId = experimentId;
        this.experimentVersion = experimentVersion;
        this.logPath = logPath;
        this.outputPath = outputPath;
        this.errorMessage = errorMessage;
    }

    public String getId() {
        return id;
    }

    public int getExperimentId() {
        return experimentId;
    }

    public int getExperimentVersion() {
        return experimentVersion;
    }

    public URI getLogPath() {
        return logPath;
    }

    public URI getOutputPath() {
        return outputPath;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        ExecutionResult that = (ExecutionResult)obj;
        return new org.apache.commons.lang3.builder.EqualsBuilder()
            .append(id, that.id)
            .append(experimentId, that.experimentId)
            .append(experimentVersion, that.experimentVersion)
            .append(logPath, that.logPath)
            .append(outputPath, that.outputPath)
            .append(errorMessage, that.errorMessage)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(experimentId)
            .append(experimentVersion)
            .append(logPath)
            .append(outputPath)
            .append(errorMessage)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("experimentId", experimentId)
            .append("experimentVersion", experimentVersion)
            .append("logPath", logPath)
            .append("outputPath", outputPath)
            .append("errorMessage", errorMessage)
            .toString();
    }
}
