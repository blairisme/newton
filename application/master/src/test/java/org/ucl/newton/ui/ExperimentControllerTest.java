package org.ucl.newton.ui;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ucl.newton.api.experiment.ExperimentDto;
import org.ucl.newton.api.experiment.ExperimentDtoBuilder;
import org.ucl.newton.common.identifier.Identifier;
import org.ucl.newton.engine.ExecutionEngine;
import org.ucl.newton.framework.*;
import org.ucl.newton.sdk.processor.DataProcessor;
import org.ucl.newton.service.experiment.ExperimentOperations;
import org.ucl.newton.service.experiment.ExperimentService;
import org.ucl.newton.service.jupyter.JupyterServer;
import org.ucl.newton.service.plugin.PluginService;
import org.ucl.newton.service.project.ProjectService;
import org.ucl.newton.service.user.UserService;
import org.ucl.newton.testobjects.DummyExperimentFactory;
import org.ucl.newton.testobjects.DummyProjectFactory;
import org.ucl.newton.testobjects.DummyUserFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class ExperimentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private ExperimentService experimentService;

    @Mock
    private ExperimentOperations experimentOperations;

    @Mock
    private ExecutionEngine executionEngine;

    @Mock
    private JupyterServer jupyterServer;

    @Mock
    private ProjectService projectService;

    @Mock
    private PluginService pluginService;

    @InjectMocks
    private ExperimentController experimentController;

    private User userZiad;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(experimentController)
                .build();
        userZiad = DummyUserFactory.createUserZiad();
    }

    @Test
    public void detailsTest() throws Exception {
        String experimentIdent = "experiment-1";
        String projectIdent = "projectIdent";
        Experiment experiment = DummyExperimentFactory.createExperiment("Experiment 1", experimentIdent);
        when(experimentService.getExperimentByIdentifier(experimentIdent)).thenReturn(experiment);
        when(userService.getAuthenticatedUser()).thenReturn(userZiad);
        when(executionEngine.isExecutionComplete(experiment)).thenReturn(true);

        mockMvc.perform(get("/project/{project}/{experiment}", projectIdent, experimentIdent)
                .param("version", "latest"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userZiad))
                .andExpect(model().attribute("experiment", experiment))
                .andExpect(model().attribute("project", experiment.getProject()))
                .andExpect(model().attribute("version", experiment.getLatestVersion()))
                .andExpect(model().attribute("executing", false))
                .andExpect(view().name("experiment/overview"));
    }

    @Test
    public void newExperimentTest() throws Exception {
        String projectIdent = "ident";
        Project project = DummyProjectFactory.createDummyProject(projectIdent, "Project name");
        Collection<DataProcessor> processors = new ArrayList<>();
        when(userService.getAuthenticatedUser()).thenReturn(userZiad);
        when(projectService.getProjectByIdentifier(projectIdent, true)).thenReturn(project);
        when(pluginService.getDataProcessors()).thenReturn(processors);

        mockMvc.perform(get("/project/{project}/new", projectIdent))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userZiad))
                .andExpect(model().attribute("project", project))
                .andExpect(model().attribute("experiment", new ExperimentDto()))
                .andExpect(model().attribute("triggerValues", new String[] {"Manual", "On data change"}))
                .andExpect(model().attribute("storageValues", new String[] {"Newton"}))
                .andExpect(model().attribute("typeValues", processors))
                .andExpect(view().name("experiment/new"));
    }

    @Test
    public void runTest() throws Exception {
        String experimentIdent = "experiment-1";
        String projectIdent = "projectIdent";
        when(experimentService.getExperimentByIdentifier(experimentIdent)).thenReturn(DummyExperimentFactory.createExperiment("Experiment 1", experimentIdent));
        mockMvc.perform(get("/project/{project}/{experiment}/run", projectIdent, experimentIdent))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/project/" + projectIdent + "/" + experimentIdent));
    }

    @Test
    public void cancelTest() throws Exception {
        String experimentIdent = "experiment-1";
        String projectIdent = "projectIdent";
        when(experimentService.getExperimentByIdentifier(experimentIdent)).thenReturn(DummyExperimentFactory.createExperiment("Experiment 1", experimentIdent));
        mockMvc.perform(get("/project/{project}/{experiment}/cancel", projectIdent, experimentIdent))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/project/" + projectIdent + "/" + experimentIdent));
    }

    @Test
    public void experimentSetupTest() throws Exception {
        String experimentIdent = "experiment-1";
        String projectIdent = "projectIdent";
        Project project = DummyProjectFactory.createDummyProject(projectIdent, "Project Name");
        Experiment experiment = DummyExperimentFactory.createExperiment("Experiment 1", experimentIdent);
        Collection<DataProcessor> processors = new ArrayList<>();

        when(userService.getAuthenticatedUser()).thenReturn(userZiad);
        when(projectService.getProjectByIdentifier(projectIdent, true)).thenReturn(project);
        when(experimentService.getExperimentByIdentifier(experimentIdent)).thenReturn(experiment);
        when(pluginService.getDataProcessors()).thenReturn(processors);

        mockMvc.perform(get("/project/{project}/{experiment}/setup", projectIdent, experimentIdent))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userZiad))
                .andExpect(model().attribute("project", project))
                .andExpect(model().attribute("experiment", experiment))
                .andExpect(model().attribute("triggerValues", new ExperimentTriggerType[] {ExperimentTriggerType.Manual, ExperimentTriggerType.Onchange}))
                .andExpect(model().attribute("storageValues", new StorageType[] {StorageType.Newton}))
                .andExpect(model().attribute("typeValues", processors))
                .andExpect(model().attribute("experimentDto", new ExperimentDto()))
                .andExpect(view().name("experiment/setup"));
    }

    @Test
    public void experimentUpdateTest() throws Exception {
        String experimentIdent = "experiment-1";
        String projectIdent = "projectIdent";
        Experiment experiment = DummyExperimentFactory.createExperiment("Experiment 1", experimentIdent);
        ExperimentDto experimentDto = ExperimentDtoBuilder.fromExperiment(experiment);

        when(experimentService.getExperimentByIdentifier(experimentIdent)).thenReturn(experiment);
        when(experimentOperations.createExperiment(experimentDto, experimentIdent)).thenReturn(experiment);

        mockMvc.perform(post("/project/{project}/{experiment}/setup", projectIdent, experimentIdent)
                .flashAttr("experimentDto", experimentDto))
                .andExpect(status().is(302))
                .andExpect(flash().attribute("message", "Update was successful"))
                .andExpect(flash().attribute("alertClass", "alert-success"))
                .andExpect(redirectedUrl("/project/" + projectIdent + "/" + experimentIdent + "/setup"));
    }

    @Test
    public void experimentUpdateTestException() throws Exception {
        String experimentIdent = "experiment-1";
        String projectIdent = "projectIdent";
        Experiment experiment = DummyExperimentFactory.createExperiment("Experiment 1", experimentIdent);
        ExperimentDto experimentDto = ExperimentDtoBuilder.fromExperiment(experiment);

        when(experimentService.getExperimentByIdentifier(experimentIdent)).thenReturn(experiment);
        when(experimentOperations.createExperiment(experimentDto, experimentIdent)).thenReturn(null);

        mockMvc.perform(post("/project/{project}/{experiment}/setup", projectIdent, experimentIdent)
                .flashAttr("experimentDto", experimentDto))
                .andExpect(status().is(302))
                .andExpect(flash().attribute("message", "Update failed null"))
                .andExpect(flash().attribute("alertClass", "alert-danger"))
                .andExpect(redirectedUrl("/project/" + projectIdent + "/" + experimentIdent + "/setup"));
    }

    @Test
    public void editTest() throws Exception {
        String experimentIdent = "experiment-1";
        String projectIdent = "projectIdent";
        URI editorUrl = new URI("someurl");

        Experiment experiment = DummyExperimentFactory.createExperiment("Experiment 1", experimentIdent);
        when(experimentService.getExperimentByIdentifier(experimentIdent)).thenReturn(experiment);

        when(userService.getAuthenticatedUser()).thenReturn(userZiad);
        when(jupyterServer.getEditorUrl(userZiad, experiment)).thenReturn(editorUrl);

        mockMvc.perform(get("/project/{project}/{experiment}/edit", projectIdent, experimentIdent))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl(editorUrl.toString()));
    }

    @Test
    public void persistNewExperimentTest() throws Exception {
        String projectIdent = "projectIdent";
        Experiment experiment = DummyExperimentFactory.createExperiment("Experiment 1", "experiment-1");
        ExperimentDto experimentDto = ExperimentDtoBuilder.fromExperiment(experiment);

        when(experimentOperations.createExperiment(experimentDto)).thenReturn(experiment);
        when(experimentService.getExperimentByIdentifier(Identifier.create("Experiment 1"))).thenReturn(null);

        mockMvc.perform(post("/project/{project}/new", projectIdent)
                .flashAttr("experiment", experimentDto))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/project/" + projectIdent));
    }

    @Test
    public void persistNewExperimentAlreadyInDbTest() throws Exception {
        String projectIdent = "projectIdent";
        Experiment experiment = DummyExperimentFactory.createExperiment("Experiment 1", "experiment-1");
        ExperimentDto experimentDto = ExperimentDtoBuilder.fromExperiment(experiment);

        when(experimentOperations.createExperiment(experimentDto)).thenReturn(experiment);
        when(experimentService.getExperimentByIdentifier("experiment-1")).thenReturn(experiment);

        mockMvc.perform(post("/project/{project}/new", projectIdent)
                .flashAttr("experiment", experimentDto))
                .andExpect(status().is(302))
                .andExpect(flash().attribute("message", "Could not create experiment as an experiment with name Experiment 1 already exists"))
                .andExpect(flash().attribute("alertClass", "alert-danger"))
                .andExpect(redirectedUrl("/project/" + projectIdent + "/new"));
    }

    @Test
    public void deleteExperimentTest() throws Exception {
        String projectIdent = "some-project";
        String experimentIdent = "some-experiment";

        mockMvc.perform(post("/project/{name}/{expName}/remove", projectIdent, experimentIdent))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/project/" + projectIdent));
    }

}
