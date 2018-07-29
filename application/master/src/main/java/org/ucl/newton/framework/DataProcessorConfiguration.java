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
 * Instances of this class contain information
 *
 * @author Blair Butterworth
 */
@Entity
@Table(name = "processor_configuration")
public class DataProcessorConfiguration
{
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "configuration_path")
    private String path;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "processor_id")
    private DataProcessor processor;

    public DataProcessorConfiguration() {
    }

    public DataProcessorConfiguration(int id, String path, DataProcessor processor) {
        this.id = id;
        this.path = path;
        this.processor = processor;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public DataProcessor getProcessor() {
        return processor;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        DataProcessorConfiguration other = (DataProcessorConfiguration)obj;
        return new EqualsBuilder()
            .append(id, other.id)
            .append(path, other.path)
            .append(processor, other.processor)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(path)
            .append(processor)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("path", path)
            .append("processor", processor)
            .toString();
    }
}
