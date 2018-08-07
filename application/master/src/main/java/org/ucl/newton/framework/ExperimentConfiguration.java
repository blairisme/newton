/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.framework;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Instances of this class represent the configuration for an experiment. That is where the data relating to
 * the experiment is stored, what processor to use, the data sources the experiment will use and the output pattern
 * that is used to define which outputs to save.
 *
 * @author John Wilkie
 * @author Blair Butterworth
 */
@Entity
@Table(name = "experiment_config")
public class ExperimentConfiguration implements Serializable {

    @Id
    @Column(name = "exp_config_id")
    @GeneratedValue(generator = "increment")
    private int id;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval=true)
    @JoinColumn(name = "storage_config_id")
    private StorageConfiguration storageConfig;

    @Column(name = "exp_proc_engine")
    private String processorPluginId;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval=true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "experiment_eds_link",
            joinColumns = @JoinColumn(name = "exp_config_id", referencedColumnName = "exp_config_id"),
            inverseJoinColumns = @JoinColumn( name = "eds_id", referencedColumnName = "eds_id"))
    private Collection<ExperimentDataSource> dataSources;

    @Column(name = "exp_out_pattern")
    private String outputPattern;

    @Column(name = "exp_display_pattern")
    private String displayPattern;

    @Enumerated(EnumType.STRING)
    @Column(name = "exp_trigger")
    private ExperimentTriggerType trigger;

    public ExperimentConfiguration(
        StorageConfiguration storageConfig,
        String processorPluginId,
        Collection<ExperimentDataSource> dataSources,
        String outputPattern,
        String displayPattern,
        ExperimentTriggerType trigger)
    {
        this.id = 0;
        this.storageConfig = storageConfig;
        this.processorPluginId = processorPluginId;
        this.dataSources = dataSources;
        this.outputPattern = outputPattern;
        this.displayPattern = displayPattern;
        this.trigger = trigger;
    }

    public ExperimentConfiguration() {
    }

    public int getId() {
        return id;
    }

    public StorageConfiguration getStorageConfiguration() {
        return storageConfig;
    }

    public String getProcessorPluginId() {
        return processorPluginId;
    }

    public Collection<ExperimentDataSource> getExperimentDataSources() {
        return dataSources;
    }

    public String getOutputPattern() {
        return outputPattern;
    }

    public String getDisplayPattern() {
        return displayPattern;
    }

    public ExperimentTriggerType getTrigger() {
        return trigger;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return false; }
        if (obj == this) { return true; }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ExperimentConfiguration other = (ExperimentConfiguration)obj;
        return new EqualsBuilder()
            .append(storageConfig, other.storageConfig)
            .append(processorPluginId, other.processorPluginId)
            .append(outputPattern, other.outputPattern)
            .append(displayPattern, other.displayPattern)
            .append(trigger, other.trigger)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(storageConfig)
            .append(processorPluginId)
            .append(outputPattern)
            .append(displayPattern)
            .append(trigger)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("storageConfig", storageConfig)
            .append("processorConfiguration", processorPluginId)
            .append("dataSources", dataSources)
            .append("outputPattern", outputPattern)
            .append("displayPattern", displayPattern)
            .append("trigger", trigger)
            .toString();
    }
}
