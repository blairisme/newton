package org.ucl.newton.service.plugin;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.sdk.processor.DataProcessor;
import org.ucl.newton.sdk.provider.DataProvider;
import org.ucl.newton.sdk.publisher.DataPublisher;

import javax.inject.Inject;
import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@ActiveProfiles("development")
public class PluginServiceTest
{
    @Inject
    private PluginService pluginService;

    @Test
    public void getPluginTest() {
        Plugin plugin = pluginService.getPlugin("newton-python");
        Assert.assertNotNull(plugin);
        Assert.assertNotNull(plugin.getLocation());
    }

    @Test
    public void getDataProvidersTest() {
        Collection<DataProvider> providers = pluginService.getDataProviders();
        Assert.assertEquals(2, providers.size());
    }

    @Test
    public void getDataProcessorsTest() {
        Collection<DataProcessor> processors = pluginService.getDataProcessors();
        Assert.assertEquals(2, processors.size());
    }

    @Test
    public void getDataPublishersTest() {
        Collection<DataPublisher> publishers = pluginService.getDataPublishers();
        Assert.assertEquals(1, publishers.size());
    }
}
