package org.ucl.newton.framework;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import javax.persistence.*;

/**
 * Instances of this class represent a data source, which is a possible source of
 * data for an experiment.
 *
 * @author John Wilkie
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

    @Column(name = "ds_version")
    private int version;

    @Column(name = "ds_data_location")
    private String dataLocation;

    public DataSource() {}

    public DataSource(int id, String name, int version, String dataLocation) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.dataLocation = dataLocation;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public int getVersion() { return version; }

    public String getDataLocation() { return dataLocation; }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        DataSource dataSource = (DataSource)obj;
        return new EqualsBuilder()
            .append(id, dataSource.id)
            .append(name, dataSource.name)
            .append(version, dataSource.version)
            .append(dataLocation, dataSource.dataLocation)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(name)
            .append(version)
            .append(dataLocation)
            .toHashCode();
    }
}


