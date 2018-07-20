package org.ucl.newton.service.data;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.service.data.sdk.DataProvider;

import javax.inject.Inject;

import java.util.Collection;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@ActiveProfiles("development")
public class DataServiceTest {
    @Inject
    DataService dataService;

    @Test
    public void runTest() throws Exception{
        dataService.run();
        Collection<DataProvider> dataProviders = dataService.getDataProviders();
        Assert.assertEquals(dataProviders.size(),1);
    }
}
