package org.ucl.newton.fizzyo;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.sdk.plugin.PluginHostContext;
import org.ucl.newton.sdk.plugin.PluginHostStorage;
import org.ucl.newton.sdk.provider.DataProviderObserver;
import org.ucl.newton.sdk.provider.DataSource;
import org.ucl.newton.sdk.provider.DataStorage;

import java.io.File;
import java.io.FileOutputStream;
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
    public void GetFizzyoDataTest() {
        FizzyoDataProvider provider = new FizzyoDataProvider();
        GetFizzyoData getFizzyoData = new GetFizzyoData(provider);
        String[] config = {"00000000-0000-0000-0000-000000000001","A1oRkpQJ0dNX1z3RA2K2zKKaLOvE2MwzA1oRkpQJ0dNxxDoJNonZWDzaLOvE2Mwz","1514764800","1533772800","[ \"all\" ]"};
        FizzyoConfiguration fizzyoConfiguration = new FizzyoConfiguration(config);

        DataStorage storage = mock(DataStorage.class);
        try {
            when(storage.getOutputStream(any())).thenReturn(new FileOutputStream("test"));
        }catch (Exception e){
            e.printStackTrace();
        }
        provider.setStorage(storage);
        getFizzyoData.setConfiguration(fizzyoConfiguration);
        getFizzyoData.run();
        FileUtils.delete(new File("test"));

    }
}
