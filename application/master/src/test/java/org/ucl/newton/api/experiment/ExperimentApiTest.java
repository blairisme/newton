package org.ucl.newton.api.experiment;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.service.experiment.ExperimentService;
import org.ucl.newton.testobjects.DummyExperimentFactory;

import java.util.Collection;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ExperimentApiTest {

    private MockMvc mockMvc;

    @Mock
    private ExperimentService experimentService;

    @InjectMocks
    private ExperimentApi experimentApi;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(experimentApi)
                .build();
    }

    @Test
    public void getExperimentTest() throws Exception {
        Experiment experiment = DummyExperimentFactory.createExperiment("Experiment 1", "experiment-1");

        when(experimentService.getExperimentByIdentifier(experiment.getIdentifier())).thenReturn(experiment);

        mockMvc.perform(get("/api/experiment/{identifier}", experiment.getIdentifier()))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"identifier\":\"experiment-1\",\"name\":\"Experiment 1\"," +
                        "\"description\":\"A simple but brief experiment description\",\"creator\":\"xiaolong.chen@ucl.ac.uk\"," +
                        "\"project\":\"gosh-apollo\",\"trigger\":\"Manual\",\"processor\":\"Python\",\"storage\":\"Newton\"," +
                        "\"outputPattern\":\"outputs/*.csv\",\"displayPattern\":\"\",\"dataSourceLocs\":[\"newton-weather\"," +
                        "\"newton-fizzyo\"],\"dataSourceIds\":[\"myproj/data1.csv\",\"myproj/data2.json\"]}"));
    }

    @Test
    public void removeExperimentTest() throws Exception {
        Experiment experiment = DummyExperimentFactory.createExperiment("Experiment 1", "experiment-1");

        when(experimentService.getExperimentByIdentifier(experiment.getIdentifier())).thenReturn(experiment);

        mockMvc.perform(get("/api/experiment/{identifier}", experiment.getIdentifier()))
                .andExpect(status().isOk());
    }

    @Test
    public void getExperimentsTest() throws Exception {
        String projectIdent = "project-1";
        Collection<Experiment> experiments = DummyExperimentFactory.getExperimentList(1);

        when(experimentService.getExperimentsByProject(projectIdent)).thenReturn(experiments);

        mockMvc.perform(get("/api/experiments?project={identifier}", projectIdent))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"experiments\":[{\"identifier\":\"experiment_0\"," +
                        "\"name\":\"Experiment 0\",\"description\":\"A simple but brief experiment description\"," +
                        "\"creator\":\"xiaolong.chen@ucl.ac.uk\",\"project\":\"gosh-apollo\",\"trigger\":\"Manual\"," +
                        "\"processor\":\"Python\",\"storage\":\"Newton\",\"outputPattern\":\"outputs/*.csv\"," +
                        "\"displayPattern\":\"\",\"dataSourceLocs\":[\"newton-weather\",\"newton-fizzyo\"]," +
                        "\"dataSourceIds\":[\"myproj/data1.csv\",\"myproj/data2.json\"]}]}"));
    }

}
