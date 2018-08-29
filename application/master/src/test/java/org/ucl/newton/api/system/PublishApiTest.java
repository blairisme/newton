package org.ucl.newton.api.system;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.sdk.publisher.DataPublisher;
import org.ucl.newton.service.experiment.ExperimentService;
import org.ucl.newton.service.plugin.PluginService;
import org.ucl.newton.testobjects.DummyExperimentFactory;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PublishApiTest {

    private MockMvc mockMvc;

    @Mock
    private PluginService pluginService;

    @Mock
    private ExperimentService experimentService;

    @Mock
    private DataPublisher publisher;

    @InjectMocks
    private PublishApi publishApi;



    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(publishApi)
                .build();
    }

    @Test
    public void publishDataTest() throws Exception {
        Collection<DataPublisher> publishers = new ArrayList<>();
        publishers.add(publisher);
        Experiment experiment = DummyExperimentFactory.createExperiment("experiment-1", "Experiment 1");

        when(pluginService.getDataPublishers()).thenReturn(publishers);
        when(experimentService.getExperimentByIdentifier("experiment-1")).thenReturn(experiment);
        when(publisher.getIdentifier()).thenReturn("newton-DRE");

        mockMvc.perform(post("/api/plugin/publish?experiment={experiment}&version={version}", "experiment-1", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void publishDataNoPublisherTest() throws Exception {
        when(pluginService.getDataPublishers()).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/api/plugin/publish?experiment={experiment}&version={version}", "experiment-1", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}
