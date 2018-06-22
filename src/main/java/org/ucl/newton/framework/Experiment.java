/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.framework;

/**
 * Instances of this class represent a research experiment, a data science
 * exercise conducted against a given set of data.
 *
 * @author Blair Butterworth
 */
public class Experiment
{
    private String id;
    private String name;

    public Experiment(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
