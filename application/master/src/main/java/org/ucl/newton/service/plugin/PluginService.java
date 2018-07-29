/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.plugin;

import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Instances of this interface provide access to plugin data.
 *
 * @author Blair Butterworth
 */
@Service
public class PluginService
{
    private PluginRepository pluginRepository;

    @Inject
    public PluginService(PluginRepository pluginRepository) {
        this.pluginRepository = pluginRepository;
    }

    public Plugin getPlugin(String identifier) {
        return pluginRepository.getPluginByIdentifier(identifier);
    }
}
