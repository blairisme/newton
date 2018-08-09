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
 * A general purpose {@link PluginConfiguration} implementation.
 *
 * @author Blair Butterworth
 */
public class BasicConfiguration implements PluginConfiguration
{
    private String viewFragment;

    public BasicConfiguration(String viewFragment) {
        this.viewFragment = viewFragment;
    }

    @Override
    public String getViewFragment() {
        return viewFragment;
    }
}
