package org.ucl.DREDataPublisher;

import java.io.File;
import java.nio.file.Paths;

public class PublishData implements Runnable{
    DREFTPServer ftpServer;
    String filePath;
    public PublishData(DREFTPServer ftpServer, String filePath){
        this.ftpServer = ftpServer;
        this.filePath = filePath;
    }

    @Override
    public void run() {
        File file = Paths.get(filePath).toFile();
        if(file.exists())
            ftpServer.upload(file);
    }
}
