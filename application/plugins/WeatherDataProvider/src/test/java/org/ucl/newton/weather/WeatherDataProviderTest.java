package org.ucl.newton.weather;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ucl.newton.sdk.plugin.PluginVisualization;
import org.ucl.newton.sdk.provider.BasicDataSource;
import org.ucl.newton.sdk.provider.DataSource;

public class WeatherDataProviderTest
{
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
        PluginVisualization visualization = provider.getVisualization();
        Assert.assertEquals("Gathers weather data from World Weather Online weather data service.", visualization.getDescription());
    }

    @Test
    public void getNameTest() {
        PluginVisualization visualization = provider.getVisualization();
        Assert.assertEquals("Weather Data Plugin", visualization.getName());
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
