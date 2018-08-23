package org.ucl.newton.fizzyo;

import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.sdk.provider.DataProviderObserver;
import org.ucl.newton.sdk.provider.DataSource;
import org.ucl.newton.sdk.provider.DataStorage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Instances of this class provide utils for file reading.
 *
 * @author Xiaolong Chen
 */

public class GetFizzyoDataTest {

    @Test
    public void GetFizzyoDataTest() throws IOException{
        FizzyoDataProvider provider = new FizzyoDataProvider();

        GetFizzyoData getFizzyoData = new GetFizzyoData(provider);
        String[] config = {"00000000-0000-0000-0000-000000000001","A1oRkpQJ0dNX1z3RA2K2zKKaLOvE2MwzA1oRkpQJ0dNxxDoJNonZWDzaLOvE2Mwz","1514764800","1533772800","[ \"all\" ]"};
        FizzyoConfiguration fizzyoConfiguration = new FizzyoConfiguration(config);

        DataProviderObserver observer = Mockito.mock(DataProviderObserver.class);
        DataStorage storage = mock(DataStorage.class);
        when(storage.getOutputStream(any(DataSource.class))).thenReturn(new FileOutputStream("test"));

        provider.setStorage(storage);
        provider.addObserver(observer);

        getFizzyoData.setConfiguration(fizzyoConfiguration);
        getFizzyoData.run();
        FileUtils.delete(new File("test"));

        verify(storage,times(5)).getOutputStream(any(DataSource.class));
        verify(observer, times(5)).dataUpdated(any(DataSource.class));

    }
}
