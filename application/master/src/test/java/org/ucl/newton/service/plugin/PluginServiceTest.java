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
        plugin = new Plugin(1,"test-plugin","src/test/resources/jupyter.jar");
        plugins = new ArrayList<>();
        plugins.add(plugin);
        repository = mock(PluginRepository.class);
        service = new PluginService(repository);
    }

    @Test
    public void getProvidersTest(){
        Collection<DataProvider> providers = service.getDataProviders();
        Assert.assertEquals(2, providers.size());
    }
    @Test
    public void getProcessersTest(){
        when(repository.getPlugins()).thenReturn(plugins);
        Collection<DataProcessor> processors = service.getDataProcessors();
        Assert.assertEquals(1, processors.size());
    }

    @Test
    public void getPluginById(){
        when(repository.getPluginByIdentifier("test-plugin")).thenReturn(plugin);
        Plugin plugin= service.getPlugin("test-plugin");
        Assert.assertEquals("test-plugin",plugin.getIdentifier());
    }


}
