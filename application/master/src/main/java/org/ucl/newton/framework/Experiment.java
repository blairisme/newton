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
import org.ucl.newton.service.experiment.UnknownExperimentVersionException;

import javax.persistence.*;
import java.util.Collection;
import java.util.Iterator;

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
    private Collection<ExperimentVersion> versions;

    public Experiment() {
    }

    public Experiment(
        int id,
        String name,
        User creator,
        Project project,
        DataProcessorConfiguration processorConfiguration,
        Collection<DataSource> dataSources,
        Collection<ExperimentVersion> versions)
    {
        this.id = id;
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

    public Collection<ExperimentVersion> getVersions() {
        return versions;
    }

    public Experiment setVersions(Collection<ExperimentVersion> versions){
        this.versions = versions;
        return this;
    }

    public ExperimentVersion getVersionWithNum(int id){
        Iterator<ExperimentVersion> itt = versions.iterator();
        while(itt.hasNext()) {
            ExperimentVersion version = itt.next();
            if(version.getId() == id){
                return version;
            }
        }
        throw new UnknownExperimentVersionException(name, id);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;

        Experiment that = (Experiment)obj;
        return new EqualsBuilder()
            .append(this.id, that.id)
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
            .append("name", name)
            .append("creator", creator)
            .append("project", project)
            .append("processorConfiguration", processorConfiguration)
            .append("dataSources", dataSources)
            .append("versions", versions)
            .toString();
    }
}
