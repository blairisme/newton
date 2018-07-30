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
import org.ucl.newton.sdk.data.DataProvider;

import javax.inject.Inject;
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
        throw new UnsupportedOperationException();
    }
}

/*
    private DataProvider getDataProvider(SourceProvider sourceProvider) {
        try {
            String jarPath = sourceProvider.getJarPath();
            File file = new File(jarPath);
            if(!file.exists()) return null;

            URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()},this.getClass().getClassLoader());

            String providerName = sourceProvider.getProviderName();
            Class<?> c = classLoader.loadClass(providerName);
            return (DataProvider)c.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
 */