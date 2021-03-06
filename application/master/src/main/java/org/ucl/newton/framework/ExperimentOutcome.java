/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.framework;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Instances of this class represent the resulting outcome from conducting an
 * experiment.
 *
 * @author Blair Butterworth
 * @author John Wilkie
 */
@Entity
@Table(name = "outcomes")
public class ExperimentOutcome
{
    @Id
    @Column(name = "outcome_id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "outcome_name")
    private String name;

    @Column(name = "outcome_location")
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(name = "outcome_type")
    private ExperimentOutcomeType type;

    public ExperimentOutcome() {
    }

    public ExperimentOutcome(
        String name,
        String location,
        ExperimentOutcomeType type)
    {
        this(0, name, location, type);
    }

    public ExperimentOutcome(
        int id,
        String name,
        String location,
        ExperimentOutcomeType type)
    {
        this.id = id;
        this.name = name;
        this.location = location;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public ExperimentOutcome setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public ExperimentOutcomeType getType() {
        return type;
    }

    public boolean isLogType() {
        return type == ExperimentOutcomeType.Log;
    }

    public boolean isDataType() {
        return type == ExperimentOutcomeType.Data;
    }

    public boolean isVisualType() {
        return type == ExperimentOutcomeType.Visuals;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ExperimentOutcome other = (ExperimentOutcome)obj;
        return new EqualsBuilder()
            .append(this.name, other.name)
            .append(this.location, other.location)
            .append(this.type, other.type)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(name)
            .append(location)
            .append(type)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("name", name)
            .append("location", location)
            .append("type", type)
            .toString();
    }
}
