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
 * Instances of this class represent a research project, a container for
 * experiments and associated data sources.
 *
 * @author Blair Butterworth
 */
public class Project
{
    private String id;
    private String name;

    public Project(String id, String name) {
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
