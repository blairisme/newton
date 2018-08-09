package org.ucl.newton.weather;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ucl.newton.sdk.data.BasicDataSource;
import org.ucl.newton.sdk.data.DataSource;

public class WeatherDataProviderTest {
    private WeatherDataProvider provider;

    @Before
    public void setUp() {
        provider = new WeatherDataProvider();
    }

    @Test
    public void getIdentifierTest() {
        Assert.assertEquals("newton-weather", provider.getIdentifier());
    }

    @Test
    public void getDescriptionTest() {
        Assert.assertEquals("Gathers weather data from World Weather Online weather data service.", provider.getDescription());
    }

    @Test
    public void getNameTest() {
        Assert.assertEquals("Weather Data Plugin", provider.getName());
    }

    @Test
    public void getWeatherDataSource(){
        DataSource dataSource = provider.getWeatherDataSource();
        Assert.assertTrue(dataSource instanceof BasicDataSource);
    }

    @Test
    public void getDataSourcesTest(){
        Assert.assertEquals(1,provider.getDataSources().size());
    }
}
