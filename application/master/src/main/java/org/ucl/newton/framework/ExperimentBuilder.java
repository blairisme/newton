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

import java.util.List;

/**
 * Instances of this class build {@link Experiment Experiments}.
 *
 * @author Blair Butterworth
 * @author John Wilkie
 */
public class ExperimentBuilder
{
    private int id;
    private String identifier;
    private String name;
    private String description;
    private User creator;
    private Project project;
    private List<ExperimentVersion> versions;
    private ExperimentConfiguration configuration;

    public ExperimentBuilder() {
    }

    public ExperimentBuilder copyExperiment(Experiment experiment) {
        id = experiment.getId();
        identifier = experiment.getIdentifier();
        name = experiment.getName();
        description = experiment.getDescription();
        creator = experiment.getCreator();
        project = experiment.getProject();
        versions = experiment.getVersions();
        configuration = experiment.getConfiguration();
        return this;
    }

    public ExperimentBuilder addVersion(ExperimentVersion version) {
        versions.add(version);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public void setExperimentVersions(List<ExperimentVersion> versions) {
        this.versions = versions;
    }

    public void setConfiguration(ExperimentConfiguration configuration) {
        this.configuration = configuration;
    }

    public Experiment build() {
        Validate.notNull(identifier);
        Validate.notNull(name);
        Validate.notNull(creator);
        Validate.notNull(project);
        Validate.notNull(versions);
        Validate.notNull(configuration);
        return new Experiment(
            id,
            identifier,
            name,
            description,
            creator,
            project,
            versions,
            configuration);
    }
}
