/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.framework;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
    private StorageConfiguration storageConfiguration;
    private DataProcessorConfiguration processorConfiguration;
    private Collection<ExperimentDataSource> dataSources;
    private List<ExperimentVersion> versions;
    private String outputPattern;
    private ExperimentTriggerType trigger;

    public ExperimentBuilder() {
    }

    public ExperimentBuilder copyExperiment(Experiment experiment) {
        id = experiment.getId();
        identifier = experiment.getIdentifier();
        name = experiment.getName();
        description = experiment.getDescription();
        creator = experiment.getCreator();
        project = experiment.getProject();
        storageConfiguration = experiment.getStorageConfiguration();
        processorConfiguration = experiment.getProcessorConfiguration();
        dataSources = experiment.getExperimentDataSources();
        versions = experiment.getVersions();
        outputPattern = experiment.getOutputPattern();
        trigger = experiment.getTrigger();
        return this;
    }

    public ExperimentBuilder addVersion(ExperimentVersion version) {
        versions.add(version);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void generateIdentifier(String text) {
        try {
            this.identifier = URLEncoder.encode(text, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
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

    public void setStorageConfiguration(StorageConfiguration storageConfiguration) {
        this.storageConfiguration = storageConfiguration;
    }

    public void setProcessorConfiguration(DataProcessorConfiguration processorConfiguration) {
        this.processorConfiguration = processorConfiguration;
    }

    public void addDataSources(int[] dataSourceIds, String[] dataSourceLocs) {
        // should check both arrays are equal length
        dataSources = new ArrayList<>();
        if(dataSourceIds != null && dataSourceLocs != null) {
            for (int i = 0; i < dataSourceIds.length; i++) {
                dataSources.add(new ExperimentDataSource(0, dataSourceIds[i], dataSourceLocs[i]));
            }
        }
    }

    public void setExperimentVersions(List<ExperimentVersion> versions) {
        this.versions = versions;
    }

    public void setOutputPattern(String outputPattern) {
        this.outputPattern = outputPattern;
    }

    public void addTrigger(String triggerLabel) {
        if(triggerLabel.equals("Manual")){
            trigger = ExperimentTriggerType.Manual;
        } else if(triggerLabel.equals("On data change")) {
            trigger = ExperimentTriggerType.Onchange;
        }
    }

    public Experiment build() {
        return new Experiment(
            id,
            identifier,
            name,
            description,
            creator,
            project,
            storageConfiguration,
            processorConfiguration,
            dataSources,
            versions,
            outputPattern,
            trigger);
    }
}
