/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.data;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.common.exception.UnknownEntityException;
import org.ucl.newton.sdk.provider.DataProvider;
import org.ucl.newton.sdk.provider.DataProviderObserver;
import org.ucl.newton.sdk.provider.DataSource;
import org.ucl.newton.sdk.provider.DataStorage;
import org.ucl.newton.service.plugin.PluginService;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Instances of this class manage the various data providers, which provide
 * data sets to the system.
 *
 * @author Blair Butterworth
 * @author Xiaolong Chen
 */
@Service
@SuppressWarnings("unused")
public class DataService implements ApplicationListener<ContextRefreshedEvent>
{
    private static Logger logger = LoggerFactory.getLogger(DataService.class);

    private PluginService pluginService;
    private ApplicationStorage applicationStorage;
    private Collection<DataProvider> dataProviders;
    private Collection<DataProviderObserver> dataObservers;

    @Inject
    public DataService(ApplicationStorage applicationStorage, PluginService pluginService) {
        this.dataProviders = null;
        this.pluginService = pluginService;
        this.applicationStorage = applicationStorage;
        this.dataObservers = new ArrayList<>();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        loadDefaultData();
        loadProviders();
    }

    private void loadProviders() {
        if (dataProviders == null) {
            dataProviders = new ArrayList<>();
            for (DataProvider dataProvider : pluginService.getDataProviders()) {
                dataProvider.setStorage(createDataStorage(dataProvider));
                dataObservers.forEach(observer -> dataProvider.addObserver(observer));

                dataProvider.start();
                dataProviders.add(dataProvider);
            }
        }
    }

    public void addDataObserver(DataProviderObserver observer) {
        dataObservers.add(observer);
        if (dataProviders != null) {
            dataProviders.forEach(provider -> provider.addObserver(observer));
        }
    }

    public DataSource getDataSource(String identifier) throws UnknownEntityException {
        for (DataProvider dataProvider: getDataProviders()) {
            for (DataSource dataSource: dataProvider.getDataSources()) {
                if (Objects.equals(dataSource.getIdentifier(), identifier)){
                    return dataSource;
                }
            }
        }
        throw new UnknownEntityException(identifier);
    }

    public DataStorageProvider getDataStorage(DataProvider dataProvider) {
        DataStorage dataStorage = dataProvider.getStorage();
        if (dataStorage instanceof DataStorageProvider) {
            return (DataStorageProvider)dataStorage;
        }
        return createDataStorage(dataProvider);
    }

    private DataStorageProvider createDataStorage(DataProvider dataProvider) {
        return new DataStorageProvider(applicationStorage);
    }

    private Collection<DataProvider> getDataProviders() {
        loadProviders();
        return dataProviders;
    }

    private void loadDefaultData() {
        try {
            Resource defaultData = new ClassPathResource("/data");
            File source = defaultData.getFile();
            File destination = applicationStorage.getDataDirectory().toFile();
            FileUtils.copyDirectory(source, destination);
        }
        catch (IOException error){
            logger.warn("Unable to copy default data", error);
        }
    }
}
