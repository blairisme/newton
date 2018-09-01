package org.ucl.newton.fizzyo;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.newton.common.network.BasicHttpConnection;
import org.ucl.newton.sdk.provider.DataProviderObserver;
import org.ucl.newton.sdk.provider.DataSource;
import org.ucl.newton.sdk.provider.DataStorage;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.mockito.internal.verification.VerificationModeFactory.times;


/**
 * Instances of this class provide utils for file reading.
 *
 * @author Xiaolong Chen
 */

public class GetFizzyoDataTest {

    @Test
    public void GetFizzyoDataTest() throws IOException{
        File file = new File(getClass().getResource("/FizzyoResponse.json").getFile());
        String fizzyoData = FileUtils.readFileToString(file,"utf-8");
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fizzyoData.getBytes(StandardCharsets.UTF_8));

        BasicHttpConnection connection = Mockito.mock(BasicHttpConnection.class);
        Mockito.when(connection.getInputStream(Mockito.any())).thenReturn(inputStream);

        FizzyoDataProvider provider = new FizzyoDataProvider();
        GetFizzyoData getFizzyoData = new GetFizzyoData(provider, connection);

        String[] config = {"00000000-0000-0000-0000-000000000001","A1oRkpQJ0dNX1z3RA2K2zKKaLOvE2MwzA1oRkpQJ0dNxxDoJNonZWDzaLOvE2Mwz","1514764800","1533772800","[ \"all\" ]"};
        FizzyoConfiguration fizzyoConfiguration = new FizzyoConfiguration();
        fizzyoConfiguration.setValues(config);

        DataProviderObserver observer = Mockito.mock(DataProviderObserver.class);
        DataStorage storage = Mockito.mock(DataStorage.class);
        Mockito.when(storage.getOutputStream(Mockito.any(DataSource.class))).thenReturn(new FileOutputStream("test"));

        provider.setStorage(storage);
        provider.addObserver(observer);

        getFizzyoData.setConfiguration(fizzyoConfiguration);
        getFizzyoData.run();
        FileUtils.deleteQuietly(new File("test"));

        Mockito.verify(storage,times(6)).getOutputStream(Mockito.any(DataSource.class));
        Mockito.verify(observer,times(6)).dataUpdated(Mockito.any(DataSource.class));
    }
}
