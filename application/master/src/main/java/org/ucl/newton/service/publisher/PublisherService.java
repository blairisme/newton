package org.ucl.newton.service.publisher;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.ucl.newton.common.file.FileUtils;
import org.ucl.newton.sdk.publisher.DataPublisher;
import org.ucl.newton.service.plugin.PluginService;

import javax.inject.Inject;
import java.io.File;
import java.net.URL;
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
    @Inject
    public PublisherService(PluginService pluginService) {
        this.pluginService = pluginService;
    }

    public DataPublisher getDREDataPublisher(){
        if (publishers == null){
            loadDataPublishers();
        }
        for ( DataPublisher publisher : getDataPublishers()){
            if("newton-DRE".equals(publisher.getIdentifier())){
                return publisher;
            }
        }
        return null;
    }
    private void loadDataPublishers(){
        publishers = new ArrayList<>();
        for(DataPublisher publisher : pluginService.getDataPublishers()){
            String configFileName = publisher.getConfigName();
            Class<?> configClass = publisher.getConfigClass();
            URL configFile = getClass().getResource("/plugins/config/"+configFileName);
            if(configFile != null) {
                File file = new File(configFile.getFile());
                String configStr = FileUtils.readFile(file);
                Gson gson = new Gson();
                publisher.setConfig(gson.fromJson(configStr, configClass));
            }
            publishers.add(publisher);
        }
    }

    private Collection<DataPublisher> getDataPublishers(){
        return publishers;
    }


}
