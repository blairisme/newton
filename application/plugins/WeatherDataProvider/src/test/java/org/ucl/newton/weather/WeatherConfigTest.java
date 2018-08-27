package org.ucl.newton.weather;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.sdk.plugin.PluginHostContext;
import org.ucl.newton.sdk.plugin.PluginHostStorage;
import org.ucl.newton.weather.model.WeatherProperty;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class WeatherConfigTest {
    public WeatherConfig config;
    public PluginHostContext context;
    public PluginHostStorage storage;
    @Before
    public void setUp() throws IOException {
        String[] properties = {"city","country","date","key","interval"};
        WeatherProperty property = new WeatherProperty(properties);
        List<WeatherProperty> weatherList = new ArrayList<>();
        weatherList.add(property);
        config = new WeatherConfig(weatherList);

        context = mock(PluginHostContext.class);
        storage = mock(PluginHostStorage.class);
        when(storage.getOutputStream(anyString())).thenReturn(new FileOutputStream("test"));
        when(context.getStorage()).thenReturn(storage);
        config.setContext(context);
    }
    @Test
    public void getValuesTest(){
        Map<String,String> values = config.getValues();
        Assert.assertEquals("city_country_date_key_interval",values.get("allWeatherList"));
    }
    @Test
    public void setValuesTest() throws IOException{
        Map<String,List<String>> values = new HashMap<>();
        values.put("items",buildList("city_country_date_interval"));
        values.put("key",buildList("key"));
        config.setValues(values);

        verify(context,times(1)).getStorage();
        verify(storage,times(1)).getOutputStream(anyString());

        FileUtils.delete(new File("test"));

    }

    private List<String> buildList(String s) {
        List<String> list = new ArrayList<>();
        list.add(s);
        return list;
    }
}
