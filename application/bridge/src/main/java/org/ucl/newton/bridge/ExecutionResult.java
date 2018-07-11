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
import java.time.Duration;
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
    private int ownerId;
    private int ownerVersion;
    private URI logPath;
    private URI dataPath;
    private URI visualsPath;
    //private Date date;
    //private Duration duration;
    private String error;

    public ExecutionResult() {
    }

    public ExecutionResult(
        String id,
        int ownerId,
        int ownerVersion,
        URI logPath,
        URI dataPath,
        URI visualPath,
        Date date,
        Duration duration,
        String error)
    {
        this.id = id;
        this.ownerId = ownerId;
        this.ownerVersion = ownerVersion;
        this.logPath = logPath;
        this.dataPath = dataPath;
        this.visualsPath = visualPath;
       // this.date = date;
        //this.duration = duration;
        this.error = error;
    }

    public String getId() {
        return id;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getOwnerVersion() {
        return ownerVersion;
    }

    public URI getLogPath() {
        return logPath;
    }

    public URI getDataPath() {
        return dataPath;
    }

    public URI getVisualsPath() {
        return visualsPath;
    }

//    public Date getDate() {
//        return date;
//    }
//
//    public Duration getDuration() {
//        return duration;
//    }

    public String getError() {
        return error;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        ExecutionResult that = (ExecutionResult)obj;
        return new EqualsBuilder()
            .append(ownerId, that.ownerId)
            .append(ownerVersion, that.ownerVersion)
            .append(id, that.id)
            .append(logPath, that.logPath)
            .append(dataPath, that.dataPath)
            .append(visualsPath, that.visualsPath)
//            .append(date, that.date)
//            .append(duration, that.duration)
            .append(error, that.error)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(ownerId)
            .append(ownerVersion)
            .append(logPath)
            .append(dataPath)
            .append(visualsPath)
//            .append(date)
//            .append(duration)
            .append(error)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("ownerId", ownerId)
            .append("ownerVersion", ownerVersion)
            .append("logPath", logPath)
            .append("dataPath", dataPath)
            .append("visualsPath", visualsPath)
//            .append("date", date)
//            .append("duration", duration)
            .append("error", error)
            .toString();
    }
}
