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
import org.ucl.newton.bridge.ExecutionResult;

/**
 * Instances of this class build {@link ExperimentVersion ExperimentVersions}.
 *
 * @author Blair Butterworth
 */
public class ExperimentVersionBuilder
{
    private Experiment experiment;
    private ExecutionResult result;

    public ExperimentVersionBuilder() {
        experiment = null;
        result = null;
    }

    public ExperimentVersionBuilder forExperiemnt(Experiment experiment) {
        this.experiment = experiment;
        return this;
    }

    public ExperimentVersionBuilder fromExecution(ExecutionResult result) {
        this.result = result;
        return this;
    }

    public ExperimentVersion build() {
        Validate.notNull(experiment);
        Validate.notNull(result);


        throw new UnsupportedOperationException();

    }
}
