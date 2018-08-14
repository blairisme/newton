/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.weather;

import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.newton.sdk.provider.DataProviderObserver;
import org.ucl.newton.sdk.provider.DataSource;
import org.ucl.newton.sdk.provider.DataStorage;
import org.ucl.newton.weather.model.WeatherProperty;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GetWeatherDataTest
{
    @Test
    public void runTest() throws IOException {
        DataProviderObserver observer = Mockito.mock(DataProviderObserver.class);
        DataStorage storage = mock(DataStorage.class);
        when(storage.getOutputStream(any(DataSource.class))).thenReturn(mock(OutputStream.class));

        WeatherDataProvider provider = new WeatherDataProvider();
        provider.setStorage(storage);
        provider.addObserver(observer);

        String[] properties = {"london","united kingdom","2018-08-1","0252e94bd710446c908123539182906"};
        List<WeatherProperty> weatherList = new ArrayList<>();
        weatherList.add(new WeatherProperty(properties));
        GetWeatherData getWeatherData = new GetWeatherData(provider);
        getWeatherData.setWeatherList(weatherList);
        getWeatherData.run();

        Mockito.verify(observer, times(1)).dataUpdated(provider.getWeatherDataSource());
        Mockito.verify(storage, times(1)).getOutputStream(provider.getWeatherDataSource());
    }
}