package org.ucl.newton.service.experiment;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.framework.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@ActiveProfiles("development")
public class ExperimentRepositoryTest {

    @Inject
    private ExperimentRepository repository;

    @Test
    public void testGetExperimentById() {
        Experiment experiment = repository.getExperimentById(1);
        Project parentProject = experiment.getProject();
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
            Assert.assertEquals(fizzyoName, experiment.getProject().getIdentifier());
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
        Assert.assertEquals(1, v1.getNumber());
    }

    @Test
    public void testProcessorConfiguration() {
        Experiment experiment = repository.getExperimentById(1);
        DataProcessorConfiguration configuration = experiment.getProcessorConfiguration();
        DataProcessor processor = configuration.getProcessor();

        Assert.assertEquals(1, configuration.getId());
        Assert.assertEquals("config.json", configuration.getPath());
        Assert.assertEquals(1, processor.getId());
        Assert.assertEquals("https://github.com/blairisme/newton", processor.getRepoUrl());
        Assert.assertEquals("test.py", processor.getNameOfInitialScript());
        Assert.assertEquals("python", processor.getProcessEngine());
    }

    @Test
    public void testDataSources() {
        Collection<DataSource> expected = new ArrayList<>();
        expected.add(new DataSource(1, "Weather temp", 1, "some/loc1"));
        expected.add(new DataSource(2, "Weather rain", 1, "some/loc2"));

        Experiment experiment = repository.getExperimentById(1);
        Collection<DataSource> actual = experiment.getDataSources();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testExperimentVersionOutcomes() {
        Experiment experiment = repository.getExperimentById(1);
        Collection<ExperimentVersion> versions = experiment.getVersions();
        Assert.assertEquals(1, versions.size());

        ExperimentVersion version1 = versions.iterator().next();
        Collection<ExperimentOutcome> outcomes = version1.getOutcomes();
        Assert.assertEquals(2, outcomes.size());
        Iterator<ExperimentOutcome> it = outcomes.iterator();
        ExperimentOutcome outcome = it.next();
        ExperimentOutcome expectedOutcome = new ExperimentOutcome(1, "outcomes/v1.csv", ExperimentOutcomeType.EXPERIMENTRESULT);
        Assert.assertEquals(expectedOutcome, outcome);

        outcome = it.next();
        Assert.assertEquals(2, outcome.getId());
        Assert.assertEquals("outcomes/log.txt", outcome.getPath());
        Assert.assertEquals(ExperimentOutcomeType.EXPERIMENTLOG, outcome.getType());
    }

}
