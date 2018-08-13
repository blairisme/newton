/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.plugin;

import org.ucl.newton.sdk.plugin.PluginHostContext;
import org.ucl.newton.sdk.plugin.PluginHostStorage;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Allows plugins to access Newton system resources.
 *
 * @author Blair Butterworth
 */
@Named
public class PluginContext implements PluginHostContext
{
    private PluginStorage storage;

    @Inject
    public PluginContext(PluginStorage storage) {
        this.storage = storage;
    }

    @Override
    public PluginHostStorage getStorage() {
        return storage;
    }
}
