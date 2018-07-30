package org.ucl.newton.service.sourceProvider;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.framework.SourceProvider;

import javax.inject.Inject;
import java.util.Collection;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@ActiveProfiles("development")
public class SourceProviderRepositoryTest {
    @Inject
    private SourceProviderRepository repository;

    @Test
    public void addSourceProvider(){
        SourceProvider sourceProvider = new SourceProvider(0,"lib/WeatherDataProvider.jar","org.ucl.WeatherDataProvider.weather.WeatherDataProvider","1.0");
        SourceProvider a = repository.addSourceProvider(sourceProvider);
        Assert.assertEquals(3,a.getId());
    }
    @Test
    public void getSourceProvider(){
        Collection<SourceProvider> sourceProviders = repository.getSourceProviders();
        Assert.assertTrue(!sourceProviders.isEmpty());
    }

    @Test
    public void getSourceProviderByIdTest(){
        SourceProvider sourceProvider = repository.getSourceProviderById(1);
        Assert.assertEquals(sourceProvider.getProviderName(),"org.ucl.WeatherDataProvider.weather.WeatherDataProvider");
        Assert.assertEquals(sourceProvider.getJarPath(),"lib/WeatherDataProvider.jar");
        Assert.assertEquals(sourceProvider.getVersion(),"1.0");
    }

    @Test
    public void removeSourceProviderTest() throws Exception {
        SourceProvider sourceProvider = new SourceProvider(0,"/jarA.jar","jarAProvider","1.0");

        repository.addSourceProvider(sourceProvider);
        SourceProvider before = repository.getSourceProviderById(sourceProvider.getId());
        Assert.assertNotNull(before);

        repository.removeSourceProvider(before);
        SourceProvider after = repository.getSourceProviderById(sourceProvider.getId());
        Assert.assertNull(after);
    }
}
