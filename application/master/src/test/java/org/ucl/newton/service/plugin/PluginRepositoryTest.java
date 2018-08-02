package org.ucl.newton.service.plugin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@ActiveProfiles("development")
public class PluginRepositoryTest {
    @Inject
    private PluginRepository repository;
    private Plugin expected;

    @Before
    public void setUp(){
        expected = new Plugin(0,"test-jupyter","src/test/resources/jupyter.jar");
    }
    @Test
    public void addPlugin(){
        repository.addPlugin(expected);
        Plugin actual = repository.getPluginByIdentifier("test-jupyter");
        repository.removePlugin(actual);
        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getPluginById(){
        Plugin ret = repository.addPlugin(expected);
        Plugin actual = repository.getPluginById(ret.getId());
        repository.removePlugin(actual);
        Assert.assertEquals(expected,actual);
    }

}
