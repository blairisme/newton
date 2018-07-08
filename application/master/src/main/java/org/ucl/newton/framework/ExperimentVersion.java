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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

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

    @Column(name = "ver_name")
    private String name;

    @OneToMany
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
        String name,
        DataProcessor process,
        Collection<DataSource> dataSources,
        Collection<ExperimentOutcome> outcomes)
    {
        this(0, number, name, process, dataSources, outcomes);
    }

    private ExperimentVersion(
        int id,
        int number,
        String name,
        DataProcessor process,
        Collection<DataSource> dataSources,
        Collection<ExperimentOutcome> outcomes)
    {
        this.id = id;
        this.number = number;
        this.name = name;
        this.outcomes = outcomes;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Collection<ExperimentOutcome> getOutcomes() {
        return outcomes;
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
            .append(this.name, other.name)
            .append(this.outcomes, other.outcomes)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(number)
            .append(name)
            .append(outcomes)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("number", number)
            .append("name", name)
            .append("outcomes", outcomes)
            .toString();
    }
}
