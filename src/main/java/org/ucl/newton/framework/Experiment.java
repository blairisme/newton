/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.framework;

import javax.persistence.*;

/**
 * Instances of this class represent a research experiment, a data science
 * exercise conducted against a given set of data.
 *
 * @author Blair Butterworth
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

    //private Project owningProject;

    public Experiment() {}

    public Experiment(int id, String name) {
        this.id = id;
        this.name = name;
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
}
