/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.data;

import org.ucl.newton.service.data.plugin.WeatherDataProvider;
import org.ucl.newton.service.data.sdk.DataProvider;
import org.ucl.newton.service.data.sdk.DataProviderObserver;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Collection;

/**
 * Instances of this class manage the various data providers, which provide
 * data sets to the system.
 *
 * @author Blair Butterworth
 */
public class DataService
{
    private Collection<DataProvider> dataProviders;

    @Inject
    public DataService(Provider<DataStorage> storageProvider) {

        DataStorage dataStorage = storageProvider.get();
        dataStorage.setProviderId("weather");

        DataProvider weatherDataProvider = new WeatherDataProvider();
        weatherDataProvider.addObserver(new ProviderObserver());
        weatherDataProvider.start(dataStorage);

        this.dataProviders.add(weatherDataProvider);
    }

    private class ProviderObserver implements DataProviderObserver
    {
        @Override
        public void dataUpdated() {

        }
    }
}
