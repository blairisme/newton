/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.api.experiment;

import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.ExperimentConfiguration;
import org.ucl.newton.framework.ExperimentDataSource;

import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Builds {@link  ExperimentDto} instances from a given {@link Experiment} or
 * from an experiments constituent parts.
 *
 * @author Blair Butterworth
 */
public class ExperimentDtoBuilder
{
    private String identifier;
    private String name;
    private String description;
    private String creator;
    private String project;
    private String trigger;
    private String processor;
    private String storage;
    private String outputPattern;
    private String displayPattern;
    private String[] dataSourceLocs;
    private String[] dataSourceIds;

    public static ExperimentDto fromExperiment(Experiment experiment) {
        ExperimentDtoBuilder builder = new ExperimentDtoBuilder();

        builder.setIdentifier(experiment.getIdentifier());
        builder.setName(experiment.getName());
        builder.setDescription(experiment.getDescription());
        builder.setCreator(experiment.getCreator().getEmail());
        builder.setProject(experiment.getProject().getIdentifier());

        ExperimentConfiguration configuration = experiment.getConfiguration();
        builder.setTrigger(configuration.getTrigger().getName());
        builder.setProcessor(configuration.getProcessorPluginId());
        builder.setStorage(configuration.getStorageConfiguration().getStorageType().name());
        builder.setOutputPattern(configuration.getOutputPattern());
        builder.setDisplayPattern(configuration.getDisplayPattern());

        Collection<ExperimentDataSource> sources = configuration.getExperimentDataSources();
        builder.setDataSourceLocs(sources.stream().map(ExperimentDataSource::getDataSourceId).collect(toList()));
        builder.setDataSourceIds(sources.stream().map(ExperimentDataSource::getCustomLocation).collect(toList()));

        return builder.build();
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public void setOutputPattern(String outputPattern) {
        this.outputPattern = outputPattern;
    }

    public void setDisplayPattern(String displayPattern) {
        this.displayPattern = displayPattern;
    }

    public void setDataSourceLocs(List<String> dataSourceLocs) {
        setDataSourceLocs(dataSourceLocs.toArray(new String[0]));
    }

    public void setDataSourceLocs(String[] dataSourceLocs) {
        this.dataSourceLocs = dataSourceLocs;
    }

    public void setDataSourceIds(List<String> dataSourceIds) {
        setDataSourceIds(dataSourceIds.toArray(new String[0]));
    }

    public void setDataSourceIds(String[] dataSourceIds) {
        this.dataSourceIds = dataSourceIds;
    }

    public ExperimentDto build() {
        return new ExperimentDto(
            identifier,
            name,
            description,
            creator,
            project,
            trigger,
            processor,
            storage,
            outputPattern,
            displayPattern,
            dataSourceLocs,
            dataSourceIds);
    }
}