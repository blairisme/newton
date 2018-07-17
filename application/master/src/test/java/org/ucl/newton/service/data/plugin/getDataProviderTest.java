package org.ucl.newton.service.data.plugin;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.newton.framework.SourceProvider;
import org.ucl.newton.service.data.DataService;
import org.ucl.newton.service.data.DataStorage;
import org.ucl.newton.service.data.SourceProviderService;
import org.ucl.newton.service.data.sdk.DataProvider;


import javax.inject.Provider;

import java.io.File;
import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class getDataProviderTest {
    @Test
    public void getDataProviderTest()throws IOException {
        SourceProviderService sourceProviderService = mock(SourceProviderService.class);
        Provider<DataStorage> dataStorageProvider = mock(Provider.class);
        SourceProvider sourceProvider = mock(SourceProvider.class);
        when(sourceProvider.getJarPath()).thenReturn("lib" + File.separator + "WeatherDataProvider.jar");
        when(sourceProvider.getProviderName()).thenReturn("org.ucl.WeatherDataProvider.weather.WeatherDataProvider");
        DataService dataService = new DataService(dataStorageProvider,sourceProviderService);
        DataProvider dataProvider = dataService.getDataProvider(sourceProvider);
        Assert.assertNotNull(dataProvider);

//        StorageProvider storageProvider = mock(StorageProvider.class);
//        DataProviderObserver observer = mock(DataProviderObserver.class);
//
//        when(storageProvider.getOutputStream(anyString())).thenReturn(mock(OutputStream.class));
////        when(storageProvider.getOutputStream(anyString())).thenReturn(new FileOutputStream("D:\\Code\\Newton\\newton\\weather"));
//
//        dataProvider.addObserver(observer);
//        dataProvider.start(storageProvider);
//        sleep(5000);


    }
}
