/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.framework;

import org.apache.commons.lang3.Validate;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Instances of this class build {@link ExperimentVersion ExperimentVersions}.
 *
 * @author Blair Butterworth
 */
public class ExperimentVersionBuilder
{
    private Integer number;
    private Collection<ExperimentOutcome> outcomes;

    public ExperimentVersionBuilder() {
        number = null;
        outcomes = new ArrayList<>();
    }

    public ExperimentVersionBuilder forExperiment(Experiment experiment) {
        this.number = experiment.getVersions().size() + 1;
        return this;
    }

    public ExperimentVersionBuilder setExperimentLog(Path path) {
        outcomes.add(new ExperimentOutcome(path.toString(), ExperimentOutcomeType.EXPERIMENTLOG));
        return this;
    }

    public ExperimentVersionBuilder setExperimentOutput(Path path) {
        outcomes.add(new ExperimentOutcome(path.toString(), ExperimentOutcomeType.EXPERIMENTRESULT));
        return this;
    }

    public ExperimentVersion build() {
        Validate.notNull(number);
        Validate.notEmpty(outcomes);
        return new ExperimentVersion(number, outcomes);
    }
}
