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
import org.ucl.newton.service.plugin.PluginService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Instances of this class manage the various data providers, which provide
 * data sets to the system.
 *
 * @author Blair Butterworth
 * @author Xiaolong Chen
 */
@Service
public class DataService implements ApplicationListener<ContextRefreshedEvent>
{
    private PluginService pluginService;
    private ApplicationStorage applicationStorage;
    private Collection<DataProvider> dataProviders;

    @Inject
    public DataService(ApplicationStorage applicationStorage, PluginService pluginService) {

        this.dataProviders = new ArrayList<>();
        this.pluginService = pluginService;
        this.applicationStorage = applicationStorage;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        for (DataProvider dataProvider : pluginService.getDataProviders()){
            DataStorage dataStorage = new DataStorage(applicationStorage);
            dataStorage.setProviderId(dataProvider.getIdentifier());

            dataProvider.addObserver(new DataObserver());
            dataProvider.start(dataStorage);
            this.dataProviders.add(dataProvider);
        }
    }

    public Collection<DataProvider> getDataProviders(){
        return dataProviders;
    }
}
