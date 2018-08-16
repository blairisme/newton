package org.ucl.newton.testobjects;

import org.ucl.newton.framework.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class DummyExperimentFactory {

    public static Experiment createExperiment(String name, String identifier) throws Exception {
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

    private static  User createUser() {
        return DummyUserFactory.createUserXiaolong();
    }

    private static  Project createProject() throws Exception {
        User owner = DummyUserFactory.createUserAdmin();
        return new Project(13, "gosh-apollo", "GOSH Project Apollo", "Project description",
                "gosh.png", createDate("2017-07-07 10:09:08"), owner , new ArrayList<>(), new ArrayList<>());
    }

    private static  Date createDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(date);
    }

    private static  List<ExperimentVersion> createVersions() {
        List<ExperimentVersion> versions = new ArrayList<>();
        versions.add(new ExperimentVersion(1, new Date(), Duration.ZERO, createOutcomes()));
        return versions;
    }

    private static  List<ExperimentOutcome> createOutcomes() {
        List<ExperimentOutcome> outcomes = new ArrayList<>();
        outcomes.add(new ExperimentOutcome(4,"outcome1", "somelocation", ExperimentOutcomeType.Data));
        outcomes.add(new ExperimentOutcome(5,"logFile", "anotherlocation", ExperimentOutcomeType.Log));
        return outcomes;
    }

    private static  ExperimentConfiguration createExperimentConfiguration() {
        ExperimentConfigurationBuilder builder = new ExperimentConfigurationBuilder();
        builder.setStorageConfiguration(new StorageConfiguration(0, StorageType.Newton, "experimentlocation", "main.ipynb"));
        builder.setProcessorPluginId("Python");
        builder.addDataSources(new String[]{"newton-weather", "newton-fizzyo"}, new String[]{"myproj/data1.csv", "myproj/data2.json"});
        builder.setOutputPattern("outputs/*.csv");
        builder.addTrigger("Manual");
        return builder.build();
    }

    public static  Collection<Experiment> getExperimentList(int listLength) throws Exception {
        Collection<Experiment> experiments = new ArrayList<>();
        for(int i = 0; i < listLength; i++) {
            experiments.add(createExperiment("Experiment " + i, "experiment_" + i));
        }
        return experiments;
    }
}
