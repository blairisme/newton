package org.ucl.newton.service.publisher;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.sdk.publisher.DataPublisher;
import org.ucl.newton.service.plugin.PluginService;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Instances of this class provide access to publishers.
 *
 * @author Blair Butterworth
 * @author Xiaolong Chen
 */
@Service
public class PublisherService  {
    private static Logger logger = LoggerFactory.getLogger(PublisherService.class);

    private PluginService pluginService;
    private Collection<DataPublisher> publishers;
    private ApplicationStorage storage;
    @Inject
    public PublisherService(PluginService pluginService, ApplicationStorage storage) {
        this.pluginService = pluginService;
        this.storage = storage;
    }

    public DataPublisher getDREDataPublisher(){
        if (publishers == null){
            loadDataPublishers();
        }
        for ( DataPublisher publisher : publishers){
            if("newton-DRE".equals(publisher.getIdentifier())){
                return publisher;
            }
        }
        return null;
    }

    public Collection<DataPublisher> getPublishers(){
        if (publishers == null)
            loadDataPublishers();
        return publishers;
    }

    private void loadDataPublishers(){
        publishers = new ArrayList<>();
        for (DataPublisher publisher : pluginService.getDataPublishers()){
            String configFileName = publisher.getConfigName();
            Class<?> configClass = publisher.getConfigClass();
            Path path = Paths.get(storage.getRootPath()).resolve("publisher").resolve(configFileName);
            File configFile = path.toFile();

            if (!configFile.exists()) {
                configFile = getDefaultConfig(configFileName);
            }
            if(configFile.exists()) {
                Object config = readConfig(configFile, configClass);
                publisher.setConfig(config);
            }
            publishers.add(publisher);
        }
    }

    private Object readConfig(File configFile, Class<?> configClass) {
        try {
            String configStr = FileUtils.readFileToString(configFile, StandardCharsets.UTF_8);
            Gson gson = new Gson();
            return gson.fromJson(configStr, configClass);
        }
        catch (IOException error) {
            logger.error("Failed to read config", error);
            return null;
        }
    }

    private File getDefaultConfig(String configFileName) {
        File file = new File(getClass().getResource("/configuration/"+configFileName).getFile());
        return file;
    }
}
