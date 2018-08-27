/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.weather;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.newton.common.network.BasicHttpConnection;
import org.ucl.newton.sdk.provider.DataProviderObserver;
import org.ucl.newton.sdk.provider.DataSource;
import org.ucl.newton.sdk.provider.DataStorage;
import org.ucl.newton.weather.model.WeatherProperty;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GetWeatherDataTest
{
    @Test
    public void runTest() throws IOException {
        File file = new File(getClass().getResource("/WeatherResponse.json").getFile());
        String weatherData = FileUtils.readFileToString(file,"utf-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(weatherData.getBytes(StandardCharsets.UTF_8));

        BasicHttpConnection connection = Mockito.mock(BasicHttpConnection.class);
        Mockito.when(connection.getInputStream(Mockito.any())).thenReturn(inputStream);

        DataProviderObserver observer = mock(DataProviderObserver.class);

        DataStorage storage = mock(DataStorage.class);
        when(storage.getOutputStream(any(DataSource.class))).thenReturn(new FileOutputStream("test"));

        WeatherDataProvider provider = new WeatherDataProvider();
        provider.setStorage(storage);
        provider.addObserver(observer);

        String[] properties = {"london","united kingdom","2018-08-01","7c344b33801543f8802121232182408","1"};
        List<WeatherProperty> weatherList = new ArrayList<>();
        weatherList.add(new WeatherProperty(properties));
        WeatherConfig config = new WeatherConfig(weatherList);
        GetWeatherData getWeatherData = new GetWeatherData(provider,connection);
        getWeatherData.setWeatherConfig(config);
        WeatherConfig weatherConfig = getWeatherData.getWeatherConfig();
        getWeatherData.run();
        FileUtils.deleteQuietly(new File("test"));

        Assert.assertEquals(config,weatherConfig);
        Mockito.verify(observer, times(1)).dataUpdated(provider.getWeatherDataSource());
        Mockito.verify(storage, times(1)).getOutputStream(provider.getWeatherDataSource());
    }
}