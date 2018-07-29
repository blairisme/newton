/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.api;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ucl.newton.application.resource.ApplicationResource;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.service.plugin.Plugin;
import org.ucl.newton.service.plugin.PluginService;

import javax.inject.Inject;

/**
 * Provides an API allowing clients to access to plugin data.
 *
 * @author Blair Butterworth
 */
@Controller
@SuppressWarnings("unused")
public class PluginApi
{
    private PluginService pluginService;
    private ApplicationStorage applicationStorage;

    @Inject
    public PluginApi(PluginService pluginService, ApplicationStorage applicationStorage) {
        this.pluginService = pluginService;
        this.applicationStorage = applicationStorage;
    }

    @RequestMapping(value = "/api/plugin/processor/{processorId}", method = RequestMethod.GET)
    @ResponseBody
    public Resource getProcessor(@PathVariable("processorId") String processorId) {
        Plugin plugin = pluginService.getPlugin(processorId);
        return new ApplicationResource(plugin.getLocation());
    }
}
