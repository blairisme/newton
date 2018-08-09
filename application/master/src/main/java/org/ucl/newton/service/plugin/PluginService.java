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
import org.ucl.newton.sdk.processor.DataProcessor;
import org.ucl.newton.sdk.provider.DataProvider;
import org.ucl.newton.sdk.publisher.DataPublisher;

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
    private Collection<DataProvider> providers;
    private Collection<DataProcessor> processors;
    private Collection<DataPublisher> publishers;

    @Inject
    public PluginService(PluginRepository pluginRepository) {
        this.pluginRepository = pluginRepository;
    }

    public Plugin getPlugin(String identifier) {
        return pluginRepository.getPluginByIdentifier(identifier);
    }

    public Collection<DataProvider> getDataProviders() {
        if (providers == null) {
            providers = getPlugins(DataProvider.class);
        }
        return providers;
    }

    public Collection<DataProcessor> getDataProcessors() {
        if (processors == null) {
            processors = getPlugins(DataProcessor.class);
        }
        return processors;
    }

    public Collection<DataPublisher> getDataPublishers() {
        if (publishers == null) {
            publishers = getPlugins(DataPublisher.class);
        }
        return publishers;
    }

    private <T> Collection<T> getPlugins(Class<T> type) {
        try {
            JarClassLoader classLoader = JarClassLoader.getSystemClassLoader();
            classLoader.load(getPluginLocations());
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