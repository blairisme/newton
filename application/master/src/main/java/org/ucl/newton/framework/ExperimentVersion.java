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
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
* Instances of this class represent a unique running of an experiment.
*
* @author John Wilkie
*/
@Entity
@Table(name = "versions")
public class ExperimentVersion {

    @Id
    @Column(name = "ver_id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "ver_number")
    private int number;

    @Column(name = "ver_created")
    private Date created;

    @Column(name = "ver_duration")
    private Duration duration;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval=true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "version_outcomes",
            joinColumns = @JoinColumn(name = "ver_id", referencedColumnName = "ver_id", foreignKey = @ForeignKey(name = "fk_vo_ver")),
            inverseJoinColumns = @JoinColumn(name = "out_id", referencedColumnName = "outcome_id", foreignKey = @ForeignKey(name = "fk_vo_out"))
    )
    private Collection<ExperimentOutcome> outcomes;

    public ExperimentVersion(){
    }

    public ExperimentVersion(
        int number,
        Date created,
        Duration duration,
        Collection<ExperimentOutcome> outcomes)
    {
        this(0, number, created, duration, outcomes);
    }

    private ExperimentVersion(
        int id,
        int number,
        Date created,
        Duration duration,
        Collection<ExperimentOutcome> outcomes)
    {
        this.id = id;
        this.number = number;
        this.created = created;
        this.duration = duration;
        this.outcomes = outcomes;
    }

    public int getId() {
        return id;
    }

    public ExperimentVersion setId(int id) {
        this.id = id;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public Date getCreated() {
        return created;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getDurationDescription() {
        return DurationFormatUtils.formatDuration(duration.toMillis(), "H:mm:ss", true);
    }

    public Collection<ExperimentOutcome> getOutcomes() {
        return outcomes;
    }

    public Collection<ExperimentOutcome> getDataOutcomes() {
        return getOutcomes((outcome) -> outcome.isDataType());
    }

    public Collection<ExperimentOutcome> getDisplayOutcomes() {
        return getOutcomes((outcome) -> outcome.isVisualType());
    }

    public Collection<ExperimentOutcome> getLogOutcomes() {
        return getOutcomes((outcome) -> outcome.isLogType());
    }

    private Collection<ExperimentOutcome> getOutcomes(Predicate<ExperimentOutcome> matching) {
        return outcomes.stream().filter(matching).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        ExperimentVersion other = (ExperimentVersion)obj;
        return new EqualsBuilder()
            .append(this.id, other.id)
            .append(this.number, other.number)
            .append(this.created, other.created)
            .append(this.duration, other.duration)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(number)
            .append(created)
            .append(duration)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("number", number)
            .append("created", created)
            .append("duration", duration)
            .append("outcomes", outcomes)
            .toString();
    }
}
