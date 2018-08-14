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

/**
 * Instances of this class represent a data transfer object for experiment
 * information.
 *
 * @author John Wilkie
 * @author Blair Butterworth
 */
@SuppressWarnings("unused")
public class ExperimentDto
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

    public ExperimentDto() {
    }

    public ExperimentDto(
        String identifier,
        String name,
        String description,
        String creator,
        String project,
        String trigger,
        String processor,
        String storage,
        String outputPattern,
        String displayPattern,
        String[] dataSourceLocs,
        String[] dataSourceIds)
    {
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.project = project;
        this.trigger = trigger;
        this.processor = processor;
        this.storage = storage;
        this.outputPattern = outputPattern;
        this.displayPattern = displayPattern;
        this.dataSourceLocs = dataSourceLocs;
        this.dataSourceIds = dataSourceIds;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getOutputPattern() {
        return outputPattern;
    }

    public void setOutputPattern(String outputPattern) {
        this.outputPattern = outputPattern;
    }

    public String getDisplayPattern() {
        return displayPattern;
    }

    public void setDisplayPattern(String displayPattern) {
        this.displayPattern = displayPattern;
    }

    public String[] getDataSourceLocs() {
        return dataSourceLocs;
    }

    public void setDataSourceLocs(String[] dataSourceLocs) {
        this.dataSourceLocs = dataSourceLocs;
    }

    public String[] getDataSourceIds() {
        return dataSourceIds;
    }

    public void setDataSourceIds(String[] dataSourceIds) {
        this.dataSourceIds = dataSourceIds;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        ExperimentDto that = (ExperimentDto)obj;
        return new EqualsBuilder()
            .append(identifier, that.identifier)
            .append(name, that.name)
            .append(description, that.description)
            .append(creator, that.creator)
            .append(project, that.project)
            .append(trigger, that.trigger)
            .append(processor, that.processor)
            .append(storage, that.storage)
            .append(outputPattern, that.outputPattern)
            .append(displayPattern, that.displayPattern)
            .append(dataSourceLocs, that.dataSourceLocs)
            .append(dataSourceIds, that.dataSourceIds)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(identifier)
            .append(name)
            .append(description)
            .append(creator)
            .append(project)
            .append(trigger)
            .append(processor)
            .append(storage)
            .append(outputPattern)
            .append(displayPattern)
            .append(dataSourceLocs)
            .append(dataSourceIds)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("identifier", identifier)
            .append("name", name)
            .append("description", description)
            .append("creator", creator)
            .append("project", project)
            .append("trigger", trigger)
            .append("processor", processor)
            .append("storage", storage)
            .append("outputPattern", outputPattern)
            .append("displayPattern", displayPattern)
            .append("dataSourceLocs", dataSourceLocs)
            .append("dataSourceIds", dataSourceIds)
            .toString();
    }
}
