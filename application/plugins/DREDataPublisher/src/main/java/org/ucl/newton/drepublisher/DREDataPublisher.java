package org.ucl.newton.drepublisher;

import org.ucl.newton.sdk.publisher.DataPublisher;
import org.ucl.newton.sdk.publisher.FTPConfig;

/**
 * Instances of this class publish data into DRE.
 *
 * @author Xiaolong Chen
 */
public class DREDataPublisher implements DataPublisher {
    private FTPConfig config;
    @Override
    public void start(String filePath) {
        if (config==null)
            return;
        PublishData publishData = new PublishData(new DREFTPServer(config),filePath);
        Thread thread = new Thread(publishData);
        thread.setDaemon(true);
        publishData.run();
    }

    @Override
    public String getConfigName() {
        return "DREFTPConfig.json";
    }

    @Override
    public Class<?> getConfigClass() {
        return FTPConfig.class;
    }

    @Override
    public <T> void setConfig(T config) {
        this.config = (FTPConfig) config;
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
