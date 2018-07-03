package org.ucl.newton.service.experiment;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.framework.Experiment;

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@ActiveProfiles("development")
public class ExperimentRepositoryTest {

    @Inject
    private ExperimentRepository repository;

    @Test
    public void testGetExperimentById() {
        Experiment experiment = repository.getExperimentById(1);
        Assert.assertEquals("Experiment 1", experiment.getName());
    }
}
