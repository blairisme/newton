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
 * Provides access to host application resources.
 *
 * @author Blair Butterworth
 */
public interface PluginHostContext
{
    PluginHostStorage getStorage();
}
