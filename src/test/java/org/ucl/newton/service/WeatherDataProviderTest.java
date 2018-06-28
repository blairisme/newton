package org.ucl.newton.service;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.newton.service.data.DataStorage;
import org.ucl.newton.service.data.plugin.WeatherDataProvider;


import static java.lang.Thread.sleep;

public class WeatherDataProviderTest {
    @Test
    public void WeatherDataProviderTest() throws Exception{
        WeatherDataProvider weatherDataProvider = new WeatherDataProvider();
        weatherDataProvider.start(new DataStorage("weather"));
        sleep(5000);
    }
}
