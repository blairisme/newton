package org.ucl.newton.service.plugin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.ucl.newton.framework.Plugin;
import org.ucl.newton.sdk.data.DataProvider;
import org.ucl.newton.sdk.processor.DataProcessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PluginServiceTest {

    private PluginService service;
    private PluginRepository repository;
    private Plugin plugin;
    private List<Plugin> plugins;

    @Before
    public void setUp(){
        plugin = new Plugin(1,"test-jupyter","src/test/resources/jupyter.jar");
        Plugin plugin2 = new Plugin(2,"test-weather", "src/test/resources/WeatherDataProvider.jar");
        plugins = new ArrayList<>();
        plugins.add(plugin);
        plugins.add(plugin2);
        repository = mock(PluginRepository.class);
        when(repository.getPlugins()).thenReturn(plugins);
        service = new PluginService(repository);
    }

    @Test
    public void getProvidersTest(){
        Collection<DataProvider> providers = service.getDataProviders();
        Assert.assertEquals(1, providers.size());
    }
    @Test
    public void getProcessersTest(){
        Collection<DataProcessor> processors = service.getDataProcessors();
        Assert.assertEquals(1, processors.size());
    }

    @Test
    public void getPluginByIdentifier(){
        when(repository.getPluginByIdentifier("test-jupyter")).thenReturn(plugin);
        Plugin plugin= service.getPlugin("test-jupyter");
        Assert.assertEquals("test-jupyter",plugin.getIdentifier());
    }


}
