/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.plugin;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Instances of this class extend the behaviour of the Newton system.
 *
 * @author Blair Butterworth
 */
@Entity
@Table(name = "plugin")
public class Plugin
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "location")
    private String location;

    public Plugin() {
    }

    public Plugin(int id, String identifier, String location) {
        this.id = id;
        this.identifier = identifier;
        this.location = location;
    }

    int getId() {
        return id;
    }

    Plugin setId(int id) {
        this.id = id;
        return this;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        Plugin plugin = (Plugin)obj;
        return new EqualsBuilder()
            .append(id, plugin.id)
            .append(identifier, plugin.identifier)
            .append(location, plugin.location)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(identifier)
            .append(location)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("identifier", identifier)
            .append("location", location)
            .toString();
    }
}
