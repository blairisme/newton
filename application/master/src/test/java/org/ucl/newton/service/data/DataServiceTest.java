package org.ucl.newton.service.data;

import org.junit.Assert;
import org.junit.Ignore;
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
public class DataServiceTest
{
    @Inject
    private DataService dataService;


    @Test
    @Ignore //causes tests to hang
    public void runTest() throws Exception{
        Collection<DataProvider> dataProviders = dataService.getDataProviders();
        Assert.assertEquals(1,dataProviders.size());
    }
}
