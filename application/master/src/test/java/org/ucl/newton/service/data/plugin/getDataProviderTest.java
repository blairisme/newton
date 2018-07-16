package org.ucl.newton.service.data.plugin;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.newton.framework.SourceProvider;
import org.ucl.newton.service.data.DataService;
import org.ucl.newton.service.data.DataStorage;
import org.ucl.newton.service.data.SourceProviderService;
import org.ucl.newton.service.data.sdk.DataProvider;

import javax.inject.Provider;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class getDataProviderTest {
    @Test
    public void getDataProviderTest(){
        SourceProviderService sourceProviderService = mock(SourceProviderService.class);
        Provider<DataStorage> dataStorageProvider = mock(Provider.class);
        SourceProvider sourceProvider = mock(SourceProvider.class);
        when(sourceProvider.getJarPath()).thenReturn("D:\\Code\\Newton\\newton\\application\\master\\lib\\WeatherDataProvider.jar");
        when(sourceProvider.getProviderName()).thenReturn("org.ucl.WeatherDataProvider.weather.WeatherDataProvider");
        DataService dataService = new DataService(dataStorageProvider,sourceProviderService);
        DataProvider dataProvider = dataService.getDataProvider(sourceProvider);
        Assert.assertNotNull(dataProvider);

    }
}
