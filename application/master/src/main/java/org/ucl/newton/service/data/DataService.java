/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.data;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.sdk.data.DataProvider;
import org.ucl.newton.sdk.data.DataProviderObserver;
import org.ucl.newton.service.plugin.PluginService;
import org.ucl.newton.service.sourceProvider.SourceProviderService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Instances of this class manage the various data providers, which provide
 * data sets to the system.
 *
 * @author Blair Butterworth
 */
@Service
public class DataService implements ApplicationListener<ContextRefreshedEvent>
{
    private Collection<DataProvider> dataProviders;
    private SourceProviderService sourceProviderService;
    private ApplicationStorage applicationStorage;
    private PluginService pluginService;

    @Inject
    public DataService(SourceProviderService sourceProviderService, ApplicationStorage applicationStorage) {

        this.dataProviders = new ArrayList<>();
        this.sourceProviderService = sourceProviderService;
        this.applicationStorage = applicationStorage;
    }

    public void run(){
        for (DataProvider dataProvider : pluginService.getDataProviders()){
            DataStorage dataStorage = new DataStorage(applicationStorage);
            dataStorage.setProviderId(dataProvider.getIdentifier());

            dataProvider.addObserver(new ProviderObserver());
            dataProvider.start(dataStorage);
            this.dataProviders.add(dataProvider);
        }
    }

    public Collection<DataProvider> getDataProviders(){
        return dataProviders;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        run();
    }

    private class ProviderObserver implements DataProviderObserver
    {
        @Override
        public void dataUpdated() {

        }
    }
}
