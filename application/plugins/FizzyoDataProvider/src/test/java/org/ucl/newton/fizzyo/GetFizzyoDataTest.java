package org.ucl.newton.fizzyo;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.newton.sdk.provider.DataProviderObserver;
import org.ucl.newton.sdk.provider.DataSource;
import org.ucl.newton.sdk.provider.DataStorage;

import java.io.IOException;
import java.io.OutputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Instances of this class provide utils for file reading.
 *
 * @author Xiaolong Chen
 */

public class GetFizzyoDataTest {
    @Test
    @Ignore //it need set authCode manually every time
    public void GetFizzyoDataTest()throws IOException {
        DataProviderObserver observer = mock(DataProviderObserver.class);
        DataStorage storageProvider = mock(DataStorage.class);
        when(storageProvider.getOutputStream(any(DataSource.class))).thenReturn(mock(OutputStream.class));

        FizzyoDataProvider dataProvider = new FizzyoDataProvider();
        dataProvider.setStorage(storageProvider);
        dataProvider.addObserver(observer);

//        when(storageProvider.getOutputStream(anyString())).thenReturn(new FileOutputStream("D:\\Code\\Newton\\newton\\Fizzyo"));
        GetFizzyoData getWeatherData = new GetFizzyoData(dataProvider);
        getWeatherData.run();

        Mockito.verify(observer, times(1)).dataUpdated(Mockito.mock(DataSource.class));
        Mockito.verify(storageProvider, times(1)).getOutputStream(any(DataSource.class));
    }
}
