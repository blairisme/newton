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
import javax.xml.crypto.Data;
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

    @Test
    public void testExperimentVersionProcess() {
        Experiment experiment = repository.getExperimentById(1);
        ExperimentProcess process = experiment.getVersions().iterator().next().getProcess();
        Assert.assertEquals(1, process.getId());
        Assert.assertEquals("https://github.com/blairisme/newton", process.getRepoUrl());
        Assert.assertEquals("test.py", process.getNameOfInitialScript());
        Assert.assertEquals("python", process.getProcessEngine());
    }

    @Test
    public void testExperimentVersionDataSources() {
        Experiment experiment = repository.getExperimentById(1);
        Collection<ExperimentVersion> versions = experiment.getVersions();
        Assert.assertEquals(1, versions.size());

        ExperimentVersion version1 = versions.iterator().next();
        Collection<DataSource> dataSources = version1.getDataSources();
        Assert.assertEquals(2, dataSources.size());
        Iterator<DataSource> it = dataSources.iterator();
        DataSource ds = it.next();
        DataSource expected = new DataSource(1, "Weather temp", 1, "some/loc1");
        Assert.assertEquals(expected, ds);

        ds = it.next();
        Assert.assertEquals(2, ds.getId());
        Assert.assertEquals("Weather rain", ds.getName());
        Assert.assertEquals(1, ds.getVersion());
        Assert.assertEquals("some/loc2", ds.getDataLocation());
    }

    @Test
    public void testExperimentVersionOutcomes() {
        Experiment experiment = repository.getExperimentById(1);
        Collection<ExperimentVersion> versions = experiment.getVersions();
        Assert.assertEquals(1, versions.size());

        ExperimentVersion version1 = versions.iterator().next();
        Collection<Outcome> outcomes = version1.getOutcomes();
        Assert.assertEquals(2, outcomes.size());
        Iterator<Outcome> it = outcomes.iterator();
        Outcome outcome = it.next();
        Outcome expectedOutcome = new Outcome(1, "outcomes/v1.csv");
        Assert.assertEquals(expectedOutcome, outcome);

        outcome = it.next();
        Assert.assertEquals(2, outcome.getId());
        Assert.assertEquals("outcomes/v2.csv", outcome.getOutcomeLocation());
    }

}
