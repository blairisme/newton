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
        addOutcome(path, ExperimentOutcomeType.Log);
        return this;
    }

    public ExperimentVersionBuilder setExperimentLogs(Collection<Path> paths) {
        addOutcomes(paths, ExperimentOutcomeType.Log);
        return this;
    }

    public ExperimentVersionBuilder setExperimentData(Path path) {
        addOutcome(path, ExperimentOutcomeType.Data);
        return this;
    }

    public ExperimentVersionBuilder setExperimentData(Collection<Path> paths) {
        addOutcomes(paths, ExperimentOutcomeType.Data);
        return this;
    }

    public ExperimentVersionBuilder setExperimentVisuals(Path path) {
        addOutcome(path, ExperimentOutcomeType.Visuals);
        return this;
    }

    public ExperimentVersionBuilder setExperimentVisuals(Collection<Path> paths) {
        addOutcomes(paths, ExperimentOutcomeType.Visuals);
        return this;
    }

    private void addOutcome(Path path, ExperimentOutcomeType type) {
        String name = path.getFileName().toString();
        String location = path.toString();
        outcomes.add(new ExperimentOutcome(name, location, type));
    }

    private void addOutcomes(Collection<Path> paths, ExperimentOutcomeType type) {
        for (Path path: paths) {
            addOutcome(path, type);
        }
    }

    public ExperimentVersion build() {
        Validate.notNull(number);
        Validate.notEmpty(outcomes);
        return new ExperimentVersion(number, outcomes);
    }
}
