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

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name="processor_configuration_id")
    private DataProcessorConfiguration processorConfiguration;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "experiment_datasources",
        joinColumns = @JoinColumn(name = "experiement_id", referencedColumnName = "exp_id"),
        inverseJoinColumns = @JoinColumn( name = "datasource_id", referencedColumnName = "ds_id"))
    private Collection<DataSource> dataSources;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "experiment_versions",
        joinColumns = @JoinColumn(name = "experiment_id", referencedColumnName = "exp_id"),
        inverseJoinColumns = @JoinColumn(name = "version_id", referencedColumnName = "ver_id"))
    private List<ExperimentVersion> versions;

    public Experiment() {
    }

    public Experiment(
        int id,
        String identifier,
        String name,
        User creator,
        Project project,
        DataProcessorConfiguration processorConfiguration,
        Collection<DataSource> dataSources,
        List<ExperimentVersion> versions)
    {
        this.id = id;
        this.identifier = identifier;
        this.name = name;
        this.creator = creator;
        this.project = project;
        this.processorConfiguration = processorConfiguration;
        this.dataSources = dataSources;
        this.versions = versions;
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

    public User getCreator() {
        return creator;
    }

    public Project getProject() {
        return project;
    }

    public DataProcessorConfiguration getProcessorConfiguration() {
        return processorConfiguration;
    }

    public Collection<DataSource> getDataSources() {
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
            .append(this.creator, that.creator)
            .append(this.project, that.project)
            .append(this.processorConfiguration, that.processorConfiguration)
            .append(this.dataSources, that.dataSources)
            .append(this.versions, that.versions)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(id)
            .append(identifier)
            .append(name)
            .append(creator)
            .append(project)
            .append(processorConfiguration)
            .append(dataSources)
            .append(versions)
            .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("identifier", identifier)
            .append("name", name)
            .append("creator", creator)
            .append("project", project)
            .append("processorConfiguration", processorConfiguration)
            .append("dataSources", dataSources)
            .append("versions", versions)
            .toString();
    }
}
