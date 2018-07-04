package org.ucl.newton.framework;

import javax.persistence.*;

/**
 * Instances of this class represent a data source, which is a possible source of
 * data for an experiment.
 *
 * @author John Wilkie
 */

@Entity
@Table(name = "datasource")
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
}


