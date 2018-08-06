package org.ucl.newton.drepublisher;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;
import org.ucl.newton.drepublisher.DREFTPServer;
import org.ucl.newton.drepublisher.PublishData;
import org.ucl.newton.sdk.publisher.FTPConfig;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PublishDataTest {
    @Test
    public void runTest() throws IOException {

        FTPClient ftpClient = mock(FTPClient.class);
        FTPConfig config = new FTPConfig("0.0.0.0","username","password",0);
        DREFTPServer dreftpServer = new DREFTPServer(config);
        dreftpServer.setFTPClient(ftpClient);
        when(ftpClient.getReplyCode()).thenReturn(200);

        PublishData publishData = new PublishData(dreftpServer,"src/test/resources/myTestFile.txt");
        publishData.run();

        verify(ftpClient,times(1)).storeFile(any(String.class),any(InputStream.class));
    }

}
