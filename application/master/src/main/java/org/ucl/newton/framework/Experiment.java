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
    private Project parentProject;

    @ManyToOne
    private User creator;

    // Visualizer visualization

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "experiment_versions",
            joinColumns = @JoinColumn(name = "experiment_id", referencedColumnName = "exp_id", foreignKey = @ForeignKey(name = "fk_experiment_versions_exp")),
            inverseJoinColumns = @JoinColumn(name = "version_id", referencedColumnName = "ver_id", foreignKey = @ForeignKey(name = "fk_experiment_versions_ver"))
    )
    Collection<ExperimentVersion> versions;

    public Experiment() {}

    public Experiment(
        int id,
        String name,
        Project parentProject,
        User creator,
        Collection<ExperimentVersion> versions)
    {
        this.id = id;
        this.name = name;
        this.parentProject = parentProject;
        this.creator = creator;
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

    public Project getParentProject() { return parentProject; }

    public User getCreator() { return creator; }

    public Collection<ExperimentVersion> getVersions() { return versions; }
}
