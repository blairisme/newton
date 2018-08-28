package org.ucl.newton.drepublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ucl.newton.common.serialization.CsvSerializer;
import org.ucl.newton.sdk.plugin.*;
import org.ucl.newton.sdk.publisher.DataPublisher;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Instances of this class publish data into DRE.
 *
 * @author Xiaolong Chen
 */
public class DREDataPublisher implements DataPublisher {
    private static Logger logger = LoggerFactory.getLogger(DREDataPublisher.class);
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
    public String getIdentifier() {
        return "newton-DRE";
    }

    @Override
    public PluginConfiguration getConfiguration() {
        return config;
    }

    @Override
    public PluginVisualization getVisualization() {
        return new BasicVisualization("DRE Data publisher", "Publish data into DRE via FTP");
    }

    @Override
    public void setContext(PluginHostContext context) {
        InputStream input = null;
        try {
            input = context.getStorage().getInputStream("DREFTPConfiguration");
        }catch (IOException e){
            logger.error("Fail to load DRE FTP configuration and load default configuration instead:", e);
        }
        if (input == null) {
            input = getClass().getResourceAsStream("/configuration/DREFTPConfiguration");
        }
        FTPConfig config = new FTPConfig();
        readFTPConfiguration(config, input);

        config.setContext(context);
        this.config = config;
    }

    private void readFTPConfiguration(FTPConfig config, InputStream input) {
        List<String[]> configs = CsvSerializer.readCSV(input);
        if(configs.size()>0){
            config.setValues(configs.get(0));
        }
    }
}
