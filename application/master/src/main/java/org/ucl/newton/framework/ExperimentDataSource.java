package org.ucl.newton.framework;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * The ExperimentDataSource class contains information relating to a data sources for a specific experiment
 *
 * @author John Wilkie
 */
@Entity
@Table(name = "eds")
public class ExperimentDataSource {

    @Id
    @Column(name = "eds_id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "ds_id")
    private int dataSourceId;

    @Column(name = "eds_custom_location")
    private String customLocation;

    public ExperimentDataSource() { }

    public ExperimentDataSource(int id, int dataSourceId, String customLocation) {
        this.id = id;
        this.dataSourceId = dataSourceId;
        this.customLocation = customLocation;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ExperimentDataSource eds = (ExperimentDataSource) obj;
        return new EqualsBuilder()
            .append(id, eds.id)
            .append(dataSourceId, eds.dataSourceId)
            .append(customLocation, eds.customLocation)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(dataSourceId)
            .append(customLocation)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("dataSourceId", dataSourceId)
            .append("customLocation", customLocation)
            .toString();
    }
}
