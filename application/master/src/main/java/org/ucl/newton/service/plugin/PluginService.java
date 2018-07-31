/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.plugin;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.ucl.newton.common.exception.InvalidPluginException;
import org.ucl.newton.common.lang.JarClassLoader;
import org.ucl.newton.common.lang.JarInstanceLoader;
import org.ucl.newton.framework.Plugin;
import org.ucl.newton.sdk.data.DataProvider;
import org.ucl.newton.sdk.processor.DataProcessor;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

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

    public Collection<DataProvider> getDataProviders() {
        return getPlugins(DataProvider.class);
    }

    public Collection<DataProcessor> getDataProcessors() {
        return getPlugins(DataProcessor.class);
    }

    private <T> Collection<T> getPlugins(Class<T> type) {
        try {
            Collection<URL> plugins = getPluginLocations();
            JarClassLoader classLoader = new JarClassLoader(plugins);
            JarInstanceLoader instanceLoader = new JarInstanceLoader(classLoader);
            return instanceLoader.getImplementors(type, "org.ucl");
        }
        catch (ReflectiveOperationException | IOException error) {
            throw new InvalidPluginException(error);
        }
    }

    private Collection<URL> getPluginLocations() throws IOException {

        Collection<URL> result = new ArrayList<>();
        for (Plugin plugin: pluginRepository.getPlugins()) {
            Resource resource = plugin.asResource();
            result.add(resource.getURL());
        }
        return result;
    }
}