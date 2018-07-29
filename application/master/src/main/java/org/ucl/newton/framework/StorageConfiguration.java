package org.ucl.newton.framework;

import javax.persistence.*;

/**
 * Instances of this class contain information relating to how an experiment and it's related data should be stored.
 *
 * @author John Wilkie
 */

@Entity
@Table(name = "storage_configuration")
public class StorageConfiguration {

    @Id
    @Column(name = "sc_id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "sc_type")
    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    // location?

    public StorageConfiguration() { }

    public StorageConfiguration(int id, StorageType storageType) {
        this.id = id;
        this.storageType = storageType;
    }

    public StorageConfiguration(int id, String storageTypeAsString) {
        this.id = id;
        if(storageTypeAsString.equals("Newton")) {
            storageType = StorageType.Newton;
        } else {
            //throw new IllegalAccessException(storageTypeAsString);
        }
    }
}
