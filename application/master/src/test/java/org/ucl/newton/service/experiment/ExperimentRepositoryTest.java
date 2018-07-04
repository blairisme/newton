package org.ucl.newton.service.experiment;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.ExperimentVersion;
import org.ucl.newton.framework.Project;
import org.ucl.newton.framework.User;

import javax.inject.Inject;
import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@ActiveProfiles("development")
public class ExperimentRepositoryTest {

    @Inject
    private ExperimentRepository repository;

    @Test
    public void testGetExperimentById() {
        Experiment experiment = repository.getExperimentById(1);
        Project parentProject = experiment.getParentProject();
        Assert.assertEquals("Experiment 1", experiment.getName());
        Assert.assertEquals(1, experiment.getId());
        Assert.assertEquals("project-fizzyo", parentProject.getIdentifier());
    }

    @Test
    public void testGetExperimentsForProject() {
        String fizzyoName = "project-fizzyo";
        Collection<Experiment> experiments = repository.getExperimentsForProject(fizzyoName);
        Assert.assertEquals(3, experiments.size());
        for(Experiment experiment: experiments){
            Assert.assertEquals(fizzyoName, experiment.getParentProject().getIdentifier());
        }
    }

    @Test
    public void testGetExperimentsForProjectForProjectWithNoExperiments() {
        String aidsRes = "aids-research";
        Collection<Experiment> experiments = repository.getExperimentsForProject(aidsRes);
        Assert.assertEquals(0, experiments.size());
    }

    @Test
    public void testCorrectExperimentCreator() {
        int expectedCreatorId = 3;
        String expectedCreatorName = "Blair Butterworth";
        String expectedEmail = "blair.butterworth.17@ucl.ac.uk";
        Experiment experiment = repository.getExperimentById(1);
        User creator = experiment.getCreator();

        Assert.assertEquals(expectedCreatorId, creator.getId());
        Assert.assertEquals(expectedCreatorName, creator.getName());
        Assert.assertEquals(expectedEmail, creator.getEmail());
    }

    @Test
    public void testExperimentVersions() {
        Experiment experiment = repository.getExperimentById(1);
        Collection<ExperimentVersion> versions = experiment.getVersions();
        Assert.assertEquals(1, versions.size());

        ExperimentVersion v1 = versions.iterator().next();
        Assert.assertEquals(1, v1.getId());
        Assert.assertEquals("Version 1", v1.getName());
        Assert.assertEquals(1, v1.getNumber());
    }

    // Don't think this is the right class for this but not sure where to put it
    @Test
    public void testExperimentVersionProcess() {

    }

}
