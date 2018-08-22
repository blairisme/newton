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
import org.springframework.core.io.Resource;
import org.ucl.newton.application.resource.ApplicationResource;

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

    @Column(name = "location")
    private String location;

    public Plugin() {
        this(0, "");
    }

    public Plugin(String location) {
        this(0, location);
    }

    Plugin(int id, String location) {
        this.id = id;
        this.location = location;
    }

    Plugin setId(int id) {
        this.id = id;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Resource asResource() {
        return new ApplicationResource(location);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        Plugin plugin = (Plugin)obj;
        return new EqualsBuilder()
            .append(id, plugin.id)
            .append(location, plugin.location)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(location)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("location", location)
            .toString();
    }
}
