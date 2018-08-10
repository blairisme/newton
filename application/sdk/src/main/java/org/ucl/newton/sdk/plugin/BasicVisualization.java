/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.sdk.plugin;

/**
 * A general purpose {@link PluginVisualization} implementation.
 *
 * @author Blair Butterworth
 */
public class BasicVisualization implements PluginVisualization
{
    private String name;
    private String description;

    public BasicVisualization(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
