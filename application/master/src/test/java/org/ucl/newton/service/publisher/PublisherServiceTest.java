package org.ucl.newton.service.publisher;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.sdk.publisher.DataPublisher;
import org.ucl.newton.service.plugin.PluginService;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class PublisherServiceTest {

    @Test
    @Ignore
    public void getDREDataPublisherTest(){
        PluginService pluginService = mock(PluginService.class);
        ApplicationStorage storage = mock(ApplicationStorage.class);
        when(storage.getRootPath()).thenReturn("src/test/resources");

        DataPublisher dataPublisher1 = mock(DataPublisher.class);
        DataPublisher dataPublisher2 = mock(DataPublisher.class);
        when(pluginService.getDataPublishers()).thenReturn(Arrays.asList(dataPublisher1,dataPublisher2));

        when(dataPublisher1.getConfigName()).thenReturn("not exist");
        when(dataPublisher2.getConfigName()).thenReturn("not exist");
        when(dataPublisher2.getIdentifier()).thenReturn("newton-DRE");
        PublisherService publisherService = new PublisherService(pluginService,storage);

        DataPublisher publisher= publisherService.getDREDataPublisher();
        Assert.assertEquals(dataPublisher2,publisher);


    }
}
