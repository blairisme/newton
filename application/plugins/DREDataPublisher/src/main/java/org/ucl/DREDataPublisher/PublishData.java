package org.ucl.DREDataPublisher;

import java.io.File;
import java.nio.file.Paths;

/**
 * Instances of this class publish data into DRE.
 *
 * @author Xiaolong Chen
 */
public class PublishData implements Runnable{
    private DREFTPServer ftpServer;
    private String filePath;
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
