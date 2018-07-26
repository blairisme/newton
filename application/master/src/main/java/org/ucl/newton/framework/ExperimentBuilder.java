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
    private String identifier;
    private String name;
    private String description;
    private User creator;
    private Project project;
    private DataProcessorConfiguration processorConfiguration;
    private Collection<DataSource> dataSources;
    private List<ExperimentVersion> versions;

    public ExperimentBuilder() {
    }

    public ExperimentBuilder copyExperiment(Experiment experiment) {
        id = experiment.getId();
        identifier = experiment.getIdentifier();
        name = experiment.getName();
        description = experiment.getDescription();
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

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setProcessorConfiguration(DataProcessorConfiguration processorConfiguration) {
        this.processorConfiguration = processorConfiguration;
    }

    public void setDataSources(Collection<DataSource> dataSources) {
        this.dataSources = dataSources;
    }

    public Experiment build() {
        return new Experiment(
            id,
            identifier,
            name,
            description,
            creator,
            project,
            processorConfiguration,
            dataSources,
            versions);
    }
}
