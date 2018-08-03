package org.ucl.DREDataPublisher;

import org.ucl.newton.sdk.publisher.DataPublisher;



public class DREDataPublisher implements DataPublisher {

    @Override
    public void start(String filePath) {
        PublishData publishData = new PublishData(new DREFTPServer(),filePath);
        Thread thread = new Thread(publishData);
        thread.setDaemon(true);
        publishData.run();
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
