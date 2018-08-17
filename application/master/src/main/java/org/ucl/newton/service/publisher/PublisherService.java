package org.ucl.newton.service.publisher;

import org.springframework.stereotype.Service;
import org.ucl.newton.sdk.publisher.DataPublisher;
import org.ucl.newton.service.plugin.PluginService;

import javax.inject.Inject;
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
        for ( DataPublisher publisher : publishers){
            if("newton-DRE".equals(publisher.getIdentifier())){
                return publisher;
            }
        }
        return null;
    }

    private void loadDataPublishers(){
        publishers = new ArrayList<>();
        for (DataPublisher publisher : pluginService.getDataPublishers()){
            publishers.add(publisher);
        }
    }

}
