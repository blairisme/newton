package org.ucl.newton.framework;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Collection;

/**
* Instances of this class represent a unique running of an experiment.
*
* @author John Wilkie
*/
@Entity
@Table(name = "versions")
public class ExperimentVersion {

    @Id
    @Column(name = "ver_id")
    @GeneratedValue(generator = "increment")
    private int id;

    @Column(name = "ver_number")
    private int number;

    @Column(name = "ver_name")
    private String name;

    @ManyToOne
    @JoinColumn(name="process_id")
    private ExperimentProcess process;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "version_data_sources",
            joinColumns = @JoinColumn(name = "ver_id", referencedColumnName = "ver_id", foreignKey = @ForeignKey(name = "fk_vds_ver")),
            inverseJoinColumns = @JoinColumn(name = "ds_id", referencedColumnName = "ds_id", foreignKey = @ForeignKey(name = "fk_vds_ds"))
    )
    private Collection<DataSource> dataSources;

    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "version_outcomes",
            joinColumns = @JoinColumn(name = "ver_id", referencedColumnName = "ver_id", foreignKey = @ForeignKey(name = "fk_vo_ver")),
            inverseJoinColumns = @JoinColumn(name = "out_id", referencedColumnName = "outcome_id", foreignKey = @ForeignKey(name = "fk_vo_out"))
    )
    private Collection<Outcome> outcomes;

    // Likely to need console output, when it ran, duration, if it failed etc here but could go in outcomes or a status class

    public ExperimentVersion(){ }

    public ExperimentVersion(
        int id,
        int number,
        String name,
        ExperimentProcess process,
        Collection<DataSource> dataSources,
        Collection<Outcome> outcomes)
    {
        this.id = id;
        this.number = number;
        this.name = name;
        this.process = process;
        this.dataSources = dataSources;
        this.outcomes = outcomes;
    }

    public int getId() { return id; }

    public int getNumber() { return number; }

    public String getName() { return name; }

    public ExperimentProcess getProcess() { return process; }

    public Collection<DataSource> getDataSources() { return dataSources; }

    public Collection<Outcome> getOutcomes() { return outcomes; }
}
