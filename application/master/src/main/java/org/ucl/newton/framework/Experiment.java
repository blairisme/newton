/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.framework;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

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


}
