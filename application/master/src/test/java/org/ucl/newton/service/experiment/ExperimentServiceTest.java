/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */
package org.ucl.newton.service.experiment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.newton.framework.*;

import java.util.ArrayList;
import java.util.Collection;

public class ExperimentServiceTest
{

    private ExperimentService service;
    private ExperimentRepository repository;
    private String identifier = "experiment-1";
    private int id = 1;
    private Experiment expectedExperiment;

    @Before
    public void setUp() {
        repository = Mockito.mock(ExperimentRepository.class);
        service = new ExperimentService(repository);
        expectedExperiment = createDummyExperiment(id, identifier);
    }

    @Test
    public void testGetExperimentById() {
        Mockito.when(repository.getExperimentById(id)).thenReturn(expectedExperiment); //change to full
        Assert.assertEquals(expectedExperiment, service.getExperimentById(id));
    }

    @Test
    public void testGetExperimentByIdentifier() {
        Mockito.when(repository.getExperimentByIdentifier(identifier)).thenReturn(expectedExperiment);
        Assert.assertEquals(expectedExperiment, service.getExperimentByIdentifier(identifier));
    }

    @Test
    public void testGetExperimentsByParentProjectName() {
        String parentProjectName = "Project 1";
        Collection<Experiment> experimentList = new ArrayList<>();
        experimentList.add(expectedExperiment);
        Mockito.when(repository.getExperimentsForProject(parentProjectName)).thenReturn(experimentList);
        Assert.assertEquals(experimentList, service.getExperimentsByParentProjectName(parentProjectName));
    }

    @Test
    public void testAddExperiment() {
        Mockito.when(repository.addExperiment(expectedExperiment)).thenReturn(expectedExperiment);
        Assert.assertEquals(expectedExperiment, service.addExperiment(expectedExperiment));
    }

    private Experiment createDummyExperiment(int id, String identifier) {
        ExperimentBuilder builder = new ExperimentBuilder();
        builder.setName("Experiment 1");
        builder.setExperimentVersions(new ArrayList<>());
        builder.setDescription("Short description");
        builder.generateIdentifier(identifier);
        builder.setProject(createProject());
        builder.setCreator(createUser());
        builder.setConfiguration(new ExperimentConfiguration());
        return null;
    }

    private Project createProject() {
        ProjectBuilder builder = new ProjectBuilder();
        builder.generateIdentifier("Project 1");
        builder.setName("Project 1");
        builder.setOwner(createUser());
        return builder.build();
    }

    private User createUser() {
        return new User(0, "user1", "user1@ucl.ac.uk", "someImage");
    }

    private ExperimentVersion createVersion() {
        Collection<ExperimentOutcome> outcomes = new ArrayList<>();
        outcomes.add(new ExperimentOutcome(1,"results", "output/results.txt", ExperimentOutcomeType.Data));
        outcomes.add(new ExperimentOutcome(2,"log", "output/log.txt", ExperimentOutcomeType.Log));
        return new ExperimentVersion(1, outcomes);
    }

}
