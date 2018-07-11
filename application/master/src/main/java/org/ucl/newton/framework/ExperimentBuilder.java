/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.framework;

import java.util.Collection;
import java.util.List;

/**
 * Instances of this class build {@link Experiment Experiments}.
 *
 * @author Blair Butterworth
 */
public class ExperimentBuilder
{
    private int id;
    private String name;
    private User creator;
    private Project project;
    private DataProcessorConfiguration processorConfiguration;
    private Collection<DataSource> dataSources;
    private List<ExperimentVersion> versions;

    public ExperimentBuilder() {
    }

    public ExperimentBuilder copyExperiment(Experiment experiment) {
        id = experiment.getId();
        name = experiment.getName();
        creator = experiment.getCreator();
        project = experiment.getProject();
        processorConfiguration = experiment.getProcessorConfiguration();
        dataSources = experiment.getDataSources();
        versions = experiment.getVersions();
        return this;
    }

    public ExperimentBuilder addVersion(ExperimentVersion version) {
        versions.add(version);
        return this;
    }

    public Experiment build() {
        return new Experiment(
            id,
            name,
            creator,
            project,
            processorConfiguration,
            dataSources,
            versions);
    }
}
