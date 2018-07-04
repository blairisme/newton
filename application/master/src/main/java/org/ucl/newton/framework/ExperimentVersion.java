package org.ucl.newton.framework;

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

    //@ManyToOne
    //private ExperimentProcess process;

    //private Collection<DataSource> dataSources;

    //private Collection<Outcomes> outcomes;

    // Likely to need console output, when it ran, duration, if it failed etc here

    public ExperimentVersion(){ }

    public ExperimentVersion(
        int id,
        int number,
        String name,
        ExperimentProcess process)
    {
        this.id = id;
        this.number = number;
        this.name = name;
        //this.process = process;
    }

    public int getId() { return id; }

    public int getNumber() { return number; }

    public String getName() { return name; }

    public ExperimentProcess getProcess() { return null; }

    public Collection<DataSource> getDataSources() { return null; }

    public Collection<Outcome> getOutcomes() { return null; }
}
