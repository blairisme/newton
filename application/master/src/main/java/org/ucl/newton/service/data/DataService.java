/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.data;

import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.framework.SourceProvider;
import org.ucl.newton.service.data.sdk.DataProvider;
import org.ucl.newton.service.data.sdk.DataProviderObserver;
import org.ucl.newton.service.sourceProvider.SourceProviderService;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Instances of this class manage the various data providers, which provide
 * data sets to the system.
 *
 * @author Blair Butterworth
 */
@Named
public class DataService
{
    private Collection<DataProvider> dataProviders;
    private SourceProviderService sourceProviderService;
    private ApplicationStorage applicationStorage;

    @Inject
    public DataService(SourceProviderService sourceProviderService, ApplicationStorage applicationStorage) {

        this.dataProviders = new ArrayList<>();
        this.sourceProviderService = sourceProviderService;
        this.applicationStorage = applicationStorage;
    }

//    @PostConstruct
    public void run(){

        for(SourceProvider sourceProvider : sourceProviderService.getSourceProviders()){
            DataStorage dataStorage = new DataStorage(applicationStorage);
            dataStorage.setProviderId(sourceProvider.getProviderName());

            DataProvider dataProvider = getDataProvider(sourceProvider);

            dataProvider.addObserver(new ProviderObserver());
            dataProvider.start(dataStorage);
            this.dataProviders.add(dataProvider);
        }
    }

    private DataProvider getDataProvider(SourceProvider sourceProvider) {
        try {
            String jarPath = sourceProvider.getJarPath();
            File file = new File(jarPath);
            if(!file.exists())
                return null;

            URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
            String providerName = sourceProvider.getProviderName();
            Class<?> c = classLoader.loadClass(providerName);
            return (DataProvider)c.newInstance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public Collection<DataProvider> getDataProviders(){
        return dataProviders;
    }
    private class ProviderObserver implements DataProviderObserver
    {
        @Override
        public void dataUpdated() {

        }
    }
}
