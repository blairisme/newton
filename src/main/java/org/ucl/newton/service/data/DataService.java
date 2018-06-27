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

    public DataService() {

        DataProvider weatherDataProvider = new WeatherDataProvider();
        weatherDataProvider.addObserver(new ProviderObserver());
        weatherDataProvider.start(new DataStorage("weather"));

        this.dataProviders.add(weatherDataProvider);
    }

    private class ProviderObserver implements DataProviderObserver
    {
        @Override
        public void dataUpdated() {

        }
    }
}
