/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.data.plugin;

import org.junit.Test;
import org.ucl.newton.service.data.plugin.weather.GetWeatherData;
import org.ucl.newton.service.data.sdk.StorageProvider;

import java.io.IOException;
import java.io.OutputStream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetWeatherDataTest
{
    @Test
//    @Ignore // failing
    public void runTest() throws IOException {
        StorageProvider storageProvider = mock(StorageProvider.class);
        when(storageProvider.getOutputStream(anyString())).thenReturn(mock(OutputStream.class));
//        when(storageProvider.getOutputStream(anyString())).thenReturn(new FileOutputStream("D:\\Code\\Newton\\newton\\weather"));
        GetWeatherData getWeatherData = new GetWeatherData(storageProvider);
        getWeatherData.run();
    }
}