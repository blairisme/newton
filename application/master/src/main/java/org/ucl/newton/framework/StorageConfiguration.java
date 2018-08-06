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
import org.springframework.core.io.Resource;
import org.ucl.newton.application.resource.ApplicationResource;

import javax.persistence.*;

/**
 * Instances of this class contain information relating to how an
 * {@link Experiment} and it's related data should be stored.
 *
 * @author John Wilkie
 */
@Entity
@Table(name = "storage_configuration")
public class StorageConfiguration
{
    @Id
    @Column(name = "sc_id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "sc_type")
    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    @Column(name = "sc_location")
    private String storageLocation;

    @Column(name = "sc_initial_script")
    private String nameOfInitialScript;

    public StorageConfiguration() {
    }

    public StorageConfiguration(int id, StorageType type, String location, String script) {
        this.id = id;
        this.storageType = type;
        this.storageLocation = location;
        this.nameOfInitialScript = script;
    }

    public int getId() {
        return id;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public Resource getStorageLocation() {
        return new ApplicationResource(storageLocation);
    }

    public String getNameOfInitialScript() {
        return nameOfInitialScript;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        StorageConfiguration other = (StorageConfiguration) obj;
        return new EqualsBuilder()
            .append(id, other.id)
            .append(storageType, other.storageType)
            .append(storageLocation, other.storageLocation)
            .append(nameOfInitialScript, other.nameOfInitialScript)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(storageType)
            .append(storageLocation)
            .append(nameOfInitialScript)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("storageType", storageType)
            .append("storageLocation", storageLocation)
            .append("nameOfInitialScript", nameOfInitialScript)
            .toString();
    }
}
