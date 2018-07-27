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
 * Instances of this class represent a data source, which is a possible source of
 * data for an experiment.
 *
 * @author John Wilkie
 * @author Blair Butterworth
 */
@Entity
@Table(name = "datasources")
public class DataSource {

    @Id
    @Column(name = "ds_id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "ds_name")
    private String name;

    @Column(name = "ds_data_location")
    private String dataLocation;

    public DataSource() {
    }

    public DataSource(int id, String name, String dataLocation) {
        this.id = id;
        this.name = name;
        this.dataLocation = dataLocation;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDataLocation() {
        return dataLocation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        DataSource other = (DataSource)obj;
        return new EqualsBuilder()
            .append(this.id, other.id)
            .append(this.name, other.name)
            .append(this.dataLocation, other.dataLocation)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(name)
            .append(dataLocation)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("name", name)
            .append("dataLocation", dataLocation)
            .toString();
    }
}


