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
public class Outcome {

    @Id
    @Column(name = "outcome_id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "outcome_loc")
    private String outcomeLocation;

    public Outcome() { }

    public Outcome(
        int id,
        String outcomeLocation)
    {
        this.id = id;
        this.outcomeLocation = outcomeLocation;
    }

    public int getId() { return id; }

    public String getOutcomeLocation() { return outcomeLocation; }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Outcome outcome = (Outcome)obj;
        return new EqualsBuilder()
                .append(id, outcome.id)
                .append(outcomeLocation, outcome.outcomeLocation)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(outcomeLocation)
                .toHashCode();
    }
}
