package org.ucl.DREDataPublisher;

import org.ucl.DREDataPublisher.model.FTPConfig;
import org.ucl.newton.sdk.publisher.DataPublisher;


public class DREDataPublisher implements DataPublisher {
    FTPConfig config;
    @Override
    public void start(String filePath) {
        if (config==null)
            return;
        PublishData publishData = new PublishData(new DREFTPServer(config),filePath);
        Thread thread = new Thread(publishData);
        thread.setDaemon(true);
        publishData.run();
    }

    public void setConfig(FTPConfig config) {
        this.config = config;
    }

    @Override
    public String getIdentifier() {
        return "newton-DRE";
    }

    @Override
    public String getName() {
        return "DRE Data publisher";
    }

    @Override
    public String getDescription() {
        return "Publish data into DRE via FTP";
    }


}
