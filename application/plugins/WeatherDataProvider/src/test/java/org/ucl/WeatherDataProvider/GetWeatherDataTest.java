/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.WeatherDataProvider;

import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.WeatherDataProvider.weather.GetWeatherData;
import org.ucl.newton.sdk.data.DataProviderObserver;
import org.ucl.newton.sdk.data.StorageProvider;

import java.io.IOException;
import java.io.OutputStream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetWeatherDataTest
{
    @Test
    public void runTest() throws IOException {
        DataProviderObserver observer = Mockito.mock(DataProviderObserver.class);
        StorageProvider storageProvider = mock(StorageProvider.class);
        when(storageProvider.getOutputStream(anyString())).thenReturn(mock(OutputStream.class));
//        when(storageProvider.getOutputStream(anyString())).thenReturn(new FileOutputStream("D:\\Code\\Newton\\newton\\weather"));
        GetWeatherData getWeatherData = new GetWeatherData(storageProvider,observer);
        getWeatherData.run();
    }
}