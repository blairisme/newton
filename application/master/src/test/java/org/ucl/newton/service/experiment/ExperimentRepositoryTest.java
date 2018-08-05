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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

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
        Assert.assertEquals("HR classification", experiment.getName());
        Assert.assertEquals(1, experiment.getId());
        Assert.assertEquals("project-fizzyo", parentProject.getIdentifier());
        ExperimentConfiguration expConfog = experiment.getConfiguration();
        Assert.assertNotNull(expConfog.getExperimentDataSources());
        Assert.assertEquals(1, expConfog.getExperimentDataSources().size());
        Assert.assertNotNull(expConfog.getOutputPattern());
        Assert.assertEquals("newton-python", expConfog.getProcessorPluginId());
        Assert.assertNotNull(expConfog.getStorageConfiguration());
        Assert.assertNotNull(expConfog.getTrigger());
    }

    @Test
    public void testGetExperimentByIdentifier() throws Exception {
        Experiment expected = createExperiment("test experiment 1", "test-experiment-1");
        Assert.assertEquals(expected.getIdentifier(), "test-experiment-1");
        repository.addExperiment(expected);
        Experiment actual = repository.getExperimentByIdentifier("test-experiment-1");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetExperimentsForProject() {
        String fizzyoName = "project-fizzyo";
        Collection<Experiment> experiments = repository.getExperimentsForProject(fizzyoName);
        Assert.assertEquals(12, experiments.size());
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
    public void testGetExperimentsForProjectForUnknownProject() {
        String unknownProject = "asdijosdi";
        Collection<Experiment> experiments = repository.getExperimentsForProject(unknownProject);
        Assert.assertEquals(0, experiments.size());
    }


    @Test
    public void testUpdateProject() {
        Experiment experiment12 = repository.getExperimentById(12);
        Assert.assertEquals(0, experiment12.getVersions().size());
        ExperimentVersionBuilder builder = new ExperimentVersionBuilder();
        builder.forExperiment(experiment12);
        Collection<Path> outputs = new ArrayList<>();
        outputs.add(Paths.get("/outputs/log.txt"));
        outputs.add(Paths.get("/outputs/output1.png"));
        outputs.add(Paths.get("/outputs/output2.csv"));
        builder.setExperimentOutputs(outputs);
        ExperimentVersion version = builder.build();
        experiment12.addVersion(version);
        repository.update(experiment12);
        Experiment experiment12Loaded = repository.getExperimentById(12);
        Assert.assertEquals(experiment12, experiment12Loaded);
        Assert.assertEquals(1, experiment12Loaded.getVersions().size());
        Assert.assertTrue(experiment12Loaded.getVersions().contains(version));
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
    public void testExperimentVersionOutcomes() {
        Experiment experiment = repository.getExperimentById(1);
        Collection<ExperimentVersion> versions = experiment.getVersions();
        Assert.assertEquals(1, versions.size());

        ExperimentVersion version1 = versions.iterator().next();
        Collection<ExperimentOutcome> outcomes = version1.getOutcomes();
        Assert.assertEquals(4, outcomes.size());
        Iterator<ExperimentOutcome> it = outcomes.iterator();
        ExperimentOutcome outcome = it.next();
        ExperimentOutcome expectedOutcome = new ExperimentOutcome(1, "data1.json", "experiment/experiment-1/versions/1/data1.json", ExperimentOutcomeType.Data);
        Assert.assertEquals(expectedOutcome, outcome);

        outcome = it.next();
        Assert.assertEquals(2, outcome.getId());
        Assert.assertEquals("data2.json", outcome.getName());
        Assert.assertEquals("experiment/experiment-1/versions/1/data2.json", outcome.getLocation());
        Assert.assertEquals(ExperimentOutcomeType.Data, outcome.getType());
    }

    private Experiment createExperiment(String name, String identifier) throws Exception {
        ExperimentBuilder builder = new ExperimentBuilder();
        builder.setName(name);
        builder.setIdentifier(identifier);
        builder.setDescription("A simple but brief experiment description");
        builder.setCreator(createUser());
        builder.setProject(createProject());
        builder.setExperimentVersions(createVersions());
        builder.setConfiguration(createExperimentConfiguration());
        return builder.build();
    }

    private User createUser() {
        return new User(4, "Xiaolong Chen", "xiaolong.chen@ucl.ac.uk", "pp_2.jpg");
    }

    private Project createProject() throws Exception {
        User owner = new User(2, "admin", "admin@ucl.ac.uk", "pp_4.jpg");
        return new Project(13, "gosh-apollo", "GOSH Project Apollo", "Project description",
                "gosh.png", createDate("2017-07-07 10:09:08"), owner , new ArrayList<>(), new ArrayList<>());
    }

    private Date createDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(date);
    }

    private List<ExperimentVersion> createVersions() {
        List<ExperimentVersion> versions = new ArrayList<>();
        versions.add(new ExperimentVersion(1, new Date(), Duration.ZERO, createOutcomes()));
        return versions;
    }

    private List<ExperimentOutcome> createOutcomes() {
        List<ExperimentOutcome> outcomes = new ArrayList<>();
        outcomes.add(new ExperimentOutcome(4,"outcome1", "somelocation", ExperimentOutcomeType.Data));
        outcomes.add(new ExperimentOutcome(5,"logFile", "anotherlocation", ExperimentOutcomeType.Log));
        return outcomes;
    }

    private ExperimentConfiguration createExperimentConfiguration() {
        ExperimentConfigurationBuilder builder = new ExperimentConfigurationBuilder();
        builder.setStorageConfiguration(new StorageConfiguration(0, "Newton", "experimentlocation", "main.ipynb"));
        builder.setProcessorPluginId("Python");
        builder.addDataSources(new String[]{"newton-weather", "newton-fizzyo"}, new String[]{"myproj/data1.csv", "myproj/data2.json"});
        builder.setOutputPattern("outputs/*.csv");
        builder.addTrigger("Manual");
        return builder.build();
    }

}
