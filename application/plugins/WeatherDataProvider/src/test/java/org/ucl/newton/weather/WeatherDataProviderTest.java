package org.ucl.newton.weather;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ucl.newton.sdk.plugin.PluginHostContext;
import org.ucl.newton.sdk.plugin.PluginHostStorage;
import org.ucl.newton.sdk.plugin.PluginVisualization;
import org.ucl.newton.sdk.provider.BasicDataSource;
import org.ucl.newton.sdk.provider.DataSource;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class WeatherDataProviderTest
{
    private WeatherDataProvider provider;
    private PluginHostContext context;

    @Before
    public void setUp() {
        provider = new WeatherDataProvider();
        context = () -> {
            PluginHostStorage storage = mock(PluginHostStorage.class);
            try {
                when(storage.getInputStream(anyString())).thenReturn(getClass().getResourceAsStream("/configuration/weatherList"));
            }catch (IOException e){
                e.printStackTrace();
            }
            return storage;
        };
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

    @Test
    public void startTest(){
        provider.setContext(context);
        provider.start();

    }
}
