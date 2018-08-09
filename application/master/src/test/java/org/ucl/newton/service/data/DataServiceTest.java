package org.ucl.newton.service.data;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.event.ContextRefreshedEvent;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.sdk.provider.DataProvider;
import org.ucl.newton.sdk.provider.DataStorage;
import org.ucl.newton.service.plugin.PluginService;

import java.util.Arrays;
import java.util.Collection;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class DataServiceTest
{
    @Test
    public void runTest() {
        ApplicationStorage applicationStorage = Mockito.mock(ApplicationStorage.class);
        PluginService pluginService = Mockito.mock(PluginService.class);

        DataProvider provider1 = Mockito.mock(DataProvider.class);
        DataProvider provider2 = Mockito.mock(DataProvider.class);
        Mockito.when(pluginService.getDataProviders()).thenReturn(Arrays.asList(provider1, provider2));

        DataService dataService = new DataService(applicationStorage, pluginService);

        ContextRefreshedEvent event = Mockito.mock(ContextRefreshedEvent.class);
        dataService.onApplicationEvent(event);

        Collection<DataProvider> dataProviders = dataService.getDataProviders();
        Assert.assertEquals(2, dataProviders.size());
        Assert.assertTrue(dataProviders.contains(provider1));
        Assert.assertTrue(dataProviders.contains(provider2));

        Mockito.verify(provider1, times(1)).start();
        Mockito.verify(provider1, times(1)).setStorage(any(DataStorage.class));
        Mockito.verify(provider2, times(1)).start();
        Mockito.verify(provider2, times(1)).setStorage(any(DataStorage.class));
    }
}
