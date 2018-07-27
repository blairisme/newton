import org.junit.Test;
import org.ucl.FizzyoDataProvider.Fizzyo.GetFizzyoData;
import org.ucl.newton.service.data.sdk.StorageProvider;

import java.io.IOException;
import java.io.OutputStream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Instances of this class provide utils for file reading.
 *
 * @author Xiaolong Chen
 */

public class GetFizzyoDataTest {
    @Test
//    @Ignore
    public void GetFizzyoDataTest()throws IOException {
        StorageProvider storageProvider = mock(StorageProvider.class);
        when(storageProvider.getOutputStream(anyString())).thenReturn(mock(OutputStream.class));
//        when(storageProvider.getOutputStream(anyString())).thenReturn(new FileOutputStream("D:\\Code\\Newton\\newton\\weather"));
        GetFizzyoData getWeatherData = new GetFizzyoData(storageProvider);
        getWeatherData.run();
    }
}
