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
import org.ucl.newton.service.experiment.MissingVersionException;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Instances of this class represent a research experiment, a data science
 * exercise conducted against a given set of data.
 *
 * @author Blair Butterworth
 * @author John Wilkie
 */
@Entity
@Table(name = "experiments")
public class Experiment
{
    @Id
    @Column(name = "exp_id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "exp_identifier")
    private String identifier;

    @Column(name = "exp_name")
    private String name;

    @Column(name = "exp_description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "storage_config_id")
    private StorageConfiguration storageConfig;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="processor_configuration_id")
    private DataProcessorConfiguration processorConfiguration;

    @OneToMany(cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "experiment_eds_link",
        joinColumns = @JoinColumn(name = "exp_id", referencedColumnName = "exp_id"),
        inverseJoinColumns = @JoinColumn( name = "eds_id", referencedColumnName = "eds_id"))
    private Collection<ExperimentDataSource> dataSources;

    @OneToMany(cascade = {CascadeType.ALL})
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "experiment_versions",
        joinColumns = @JoinColumn(name = "experiment_id", referencedColumnName = "exp_id"),
        inverseJoinColumns = @JoinColumn(name = "version_id", referencedColumnName = "ver_id"))
    private List<ExperimentVersion> versions;

    @Column(name = "exp_out_pattern")
    private String outputPattern;

    @Enumerated(EnumType.STRING)
    @Column(name = "exp_trigger")
    private ExperimentTriggerType trigger;

    public Experiment() {
    }

    public Experiment(
        int id,
        String identifier,
        String name,
        String description,
        User creator,
        Project project,
        StorageConfiguration storageConfig,
        DataProcessorConfiguration processorConfiguration,
        Collection<ExperimentDataSource> dataSources,
        List<ExperimentVersion> versions,
        String outputPattern,
        ExperimentTriggerType trigger)
    {
        this.id = id;
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.project = project;
        this.storageConfig = storageConfig;
        this.processorConfiguration = processorConfiguration;
        this.dataSources = dataSources;
        this.versions = versions;
        this.outputPattern = outputPattern;
        this.trigger = trigger;
    }

    public int getId() {
        return id;
    }

    public Experiment setId(int id) {
        this.id = id;
        return this;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public User getCreator() {
        return creator;
    }

    public Project getProject() {
        return project;
    }

    public StorageConfiguration getStorageConfiguration() {
        return storageConfig;
    }

    public DataProcessorConfiguration getProcessorConfiguration() {
        return processorConfiguration;
    }

    public Collection<ExperimentDataSource> getExperimentDataSources() {
        return dataSources;
    }

    public List<ExperimentVersion> getVersions() {
        return versions;
    }

    public boolean hasVersion() {
        return ! versions.isEmpty();
    }

    public ExperimentVersion getVersion(int version) {
        if (version < 0) {
            throw new IllegalArgumentException();
        }
        if (version >= versions.size()) {
            throw new MissingVersionException(name, version);
        }
        return versions.get(version);
    }

    public ExperimentVersion getLatestVersion() {
        if (versions.isEmpty()) {
            throw new MissingVersionException(name, 1);
        }
        return versions.get(versions.size() - 1);
    }

    public Experiment setVersions(List<ExperimentVersion> versions){
        this.versions = versions;
        return this;
    }

    public String getOutputPattern() {
        return outputPattern;
    }

    public ExperimentTriggerType getTrigger() {
        return trigger;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        Experiment that = (Experiment)obj;
        return new EqualsBuilder()
            .append(this.id, that.id)
            .append(this.identifier, that.identifier)
            .append(this.name, that.name)
            .append(this.description, that.description)
            .append(this.creator, that.creator)
            .append(this.project, that.project)
            .append(this.storageConfig, that.storageConfig)
            .append(this.processorConfiguration, that.processorConfiguration)
            .append(this.dataSources, that.dataSources)
            .append(this.versions, that.versions)
            .append(this.outputPattern, that.outputPattern)
            .append(this.trigger, that.trigger)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(identifier)
            .append(name)
            .append(description)
            .append(creator)
            .append(project)
            .append(storageConfig)
            .append(processorConfiguration)
            .append(dataSources)
            .append(versions)
            .append(outputPattern)
            .append(trigger)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("identifier", identifier)
            .append("name", name)
            .append( "description", description)
            .append("creator", creator)
            .append("project", project)
            .append("storageConfig", storageConfig)
            .append("processorConfiguration", processorConfiguration)
            .append("dataSources", dataSources)
            .append("versions", versions)
            .append("outputPattern", outputPattern)
            .append("trigger", trigger)
            .toString();
    }
}
