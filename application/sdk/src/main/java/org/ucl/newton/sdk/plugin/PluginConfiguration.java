/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.sdk.plugin;

import java.util.List;
import java.util.Map;

/**
 * Defines methods through which the Newton system can allow users to see the
 * plugins configuration as well as being able to alter it.
 *
 * @author Blair Butterworth
 */
public interface PluginConfiguration
{
    String getViewFragment();

    Map<String, String> getValues();

    void setValues(Map<String, List<String>> values);
}
