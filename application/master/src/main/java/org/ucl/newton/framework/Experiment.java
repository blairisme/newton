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

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval=true)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "experiment_versions",
        joinColumns = @JoinColumn(name = "experiment_id", referencedColumnName = "exp_id"),
        inverseJoinColumns = @JoinColumn(name = "version_id", referencedColumnName = "ver_id"))
    private List<ExperimentVersion> versions;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval=true)
    @JoinColumn(name = "exp_config_id")
    private ExperimentConfiguration configuration;

    public Experiment() {
    }

    public Experiment(
        int id,
        String identifier,
        String name,
        String description,
        User creator,
        Project project,
        List<ExperimentVersion> versions,
        ExperimentConfiguration configuration)
    {
        this.id = id;
        this.identifier = identifier;
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.project = project;
        this.versions = versions;
        this.configuration = configuration;
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

    public Experiment setVersions(List<ExperimentVersion> versions) {
        this.versions = versions;
        return this;
    }

    public void addVersion(ExperimentVersion version) {
        versions.add(version);
    }

    public ExperimentConfiguration getConfiguration() {
        return configuration;
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
            .append(this.configuration, that.configuration)
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
            .append(configuration)
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
            .append("versions", versions)
            .append("configuration", configuration)
            .toString();
    }
}
