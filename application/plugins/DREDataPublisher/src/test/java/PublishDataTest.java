import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;
import org.ucl.DREDataPublisher.DREFTPServer;
import org.ucl.DREDataPublisher.PublishData;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class PublishDataTest {
    @Test
    public void runTest() throws IOException {

        FTPClient ftpClient = mock(FTPClient.class);
        DREFTPServer dreftpServer = new DREFTPServer();
        dreftpServer.setFTPClient(ftpClient);
        when(ftpClient.getReplyCode()).thenReturn(200);

        PublishData publishData = new PublishData(dreftpServer,"src/test/resources/myTestFile.txt");
        publishData.run();

        verify(ftpClient,times(1)).storeFile(any(String.class),any(InputStream.class));
    }
}
