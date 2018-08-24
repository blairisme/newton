/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.plugin;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.ucl.newton.common.exception.InvalidPluginException;
import org.ucl.newton.common.lang.JarClassLoader;
import org.ucl.newton.common.lang.JarInstanceLoader;
import org.ucl.newton.sdk.plugin.NewtonPlugin;
import org.ucl.newton.sdk.processor.DataProcessor;
import org.ucl.newton.sdk.provider.DataProvider;
import org.ucl.newton.sdk.provider.DataSource;
import org.ucl.newton.sdk.publisher.DataPublisher;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Instances of this interface provide access to plugin data.
 *
 * @author Blair Butterworth
 */
@Service
public class PluginService implements ApplicationListener<ContextRefreshedEvent>
{
    private PluginContext pluginContext;
    private PluginRepository pluginRepository;
    private List<DataProvider> providers;
    private List<DataProcessor> processors;
    private Collection<DataPublisher> publishers;

    @Inject
    public PluginService(PluginContext pluginContext, PluginRepository pluginRepository) {
        this.pluginContext = pluginContext;
        this.pluginRepository = pluginRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Consumer<NewtonPlugin> updateContext = plugin -> plugin.setContext(pluginContext);
        getDataProviders().forEach(updateContext);
        getDataProcessors().forEach(updateContext);
        getDataPublishers().forEach(updateContext);
    }

    public Plugin getPlugin(String identifier) {
        NewtonPlugin pluginImplementor = getPluginImplementor(identifier);
        if (pluginImplementor != null) {

            CodeSource codeSource = pluginImplementor.getClass().getProtectionDomain().getCodeSource();
            if (codeSource != null) {

                URL jarLocation = codeSource.getLocation();
                return new Plugin(jarLocation.toString());
            }
        }
        return null;
    }

    public void addPlugin(Plugin plugin) {
        pluginRepository.addPlugin(plugin);
        providers = null;
        processors = null;
        publishers = null;
    }

    public void removePlugin(Plugin plugin) {
        pluginRepository.removePlugin(plugin);
        providers = null;
        processors = null;
        publishers = null;
    }

    public Collection<DataSource> getDataSources() {
        Collection<DataSource> result = new ArrayList<>();
        for (DataProvider dataProvider: getDataProviders()) {
            result.addAll(dataProvider.getDataSources());
        }
        return result;
    }

    public Collection<DataProvider> getDataProviders() {
        if (providers == null) {
            providers = new ArrayList(getPlugins(DataProvider.class));
            providers.sort(Comparator.comparing(o -> o.getVisualization().getName()));
        }
        return providers;
    }

    public Collection<DataProcessor> getDataProcessors() {
        if (processors == null) {
            processors = new ArrayList(getPlugins(DataProcessor.class));
            processors.sort(Comparator.comparing(o -> o.getVisualization().getName()));
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
            Collection<T> result = instanceLoader.getImplementors(type, "org.ucl");
            return new CopyOnWriteArrayList<>(result);
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

    private NewtonPlugin getPluginImplementor(String identifier) {
        DataProvider provider = getPluginImplementor(getDataProviders(), identifier);
        if (provider != null) {
            return provider;
        }
        DataProcessor processor = getPluginImplementor(getDataProcessors(), identifier);
        if (processor != null) {
            return processor;
        }
        DataPublisher publisher = getPluginImplementor(getDataPublishers(), identifier);
        if (publisher != null) {
            return publisher;
        }
        return null;
    }

    private <T extends NewtonPlugin> T getPluginImplementor(Collection<T> plugins, String identifier) {
        for (T plugin: plugins) {
            if (Objects.equals(plugin.getIdentifier(), identifier)){
                return plugin;
            }
        }
        return null;
    }
}