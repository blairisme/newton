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
import org.ucl.newton.weather.GetWeatherData;
import org.ucl.newton.weather.WeatherDataProvider;
import org.ucl.newton.sdk.data.DataProviderObserver;
import org.ucl.newton.sdk.data.DataSource;
import org.ucl.newton.sdk.data.DataStorage;

import java.io.IOException;
import java.io.OutputStream;

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

//        when(storageProvider.getOutputStream(anyString())).thenReturn(new FileOutputStream("D:\\Code\\Newton\\newton\\weather"));
        GetWeatherData getWeatherData = new GetWeatherData(provider);
        getWeatherData.run();

        Mockito.verify(observer, times(1)).dataUpdated(provider.getWeatherDataSource());
        Mockito.verify(storage, times(1)).getOutputStream(provider.getWeatherDataSource());
    }
}