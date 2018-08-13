/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.api.experiment;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Represents a data transfer object containing a collection of
 * {@link ExperimentDto ExperimentDtos}.
 *
 * @author Blair Butterworth
 */
public class ExperimentDtoSet
{
    private List<ExperimentDto> experiments;

    public ExperimentDtoSet() {
    }

    public ExperimentDtoSet(List<ExperimentDto> experiments) {
        this.experiments = experiments;
    }

    public List<ExperimentDto> getExperiments() {
        return experiments;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        ExperimentDtoSet that = (ExperimentDtoSet)obj;
        return new EqualsBuilder()
            .append(experiments, that.experiments)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(experiments)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("experiments", experiments)
            .toString();
    }
}
