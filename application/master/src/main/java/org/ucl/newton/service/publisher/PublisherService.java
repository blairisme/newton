package org.ucl.newton.service.publisher;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.common.file.PathUtils;
import org.ucl.newton.sdk.publisher.DataPublisher;
import org.ucl.newton.service.plugin.PluginService;

import javax.inject.Inject;
import java.io.File;
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
        for(DataPublisher publisher : pluginService.getDataPublishers()){
            String configFileName = publisher.getConfigName();
            Class<?> configClass = publisher.getConfigClass();
            Path path = Paths.get(storage.getRootPath()).resolve("publisher").resolve(configFileName);
            File configFile = path.toFile();

            if (!configFile.exists())
                configFile = getDefaultConfig(configFileName);

            if(configFile.exists()) {
                String configStr = FileUtils.readFile(configFile);
                Gson gson = new Gson();
                publisher.setConfig(gson.fromJson(configStr, configClass));
            }
            publishers.add(publisher);
        }
    }

    private File getDefaultConfig(String configFileName) {
        Path path = PathUtils.getConfigurationPath().resolve(configFileName);
        return path.toFile();
    }


}
