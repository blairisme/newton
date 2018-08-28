/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ucl.newton.framework.DataPermission;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.Project;
import org.ucl.newton.framework.User;
import org.ucl.newton.sdk.provider.DataProvider;
import org.ucl.newton.sdk.provider.DataSource;
import org.ucl.newton.service.data.DataPermissionService;
import org.ucl.newton.service.experiment.ExperimentService;
import org.ucl.newton.service.plugin.PluginService;
import org.ucl.newton.service.project.ProjectService;
import org.ucl.newton.service.user.UserService;
import org.ucl.newton.testobjects.DummyExperimentFactory;
import org.ucl.newton.testobjects.DummyProjectFactory;
import org.ucl.newton.testobjects.DummyUserFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ProjectControllerTest
{

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private ProjectService projectService;

    @Mock
    private ExperimentService experimentService;

    @Mock
    private PluginService pluginService;

    @Mock
    private DataPermissionService dataPermissionService;

    @InjectMocks
    private ProjectController projectController;

    private User userZiad;
    private DummyProjectFactory projectFactory;
    private Project projectJiro;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(projectController)
                .build();
        userZiad = DummyUserFactory.createUserZiad();
        projectFactory = new DummyProjectFactory();
        projectJiro = projectFactory.createProjectGoshJiro();
    }

    @Test
    public void listTest() throws Exception {
        Collection<Project> projects = projectFactory.getProjects(userZiad);
        Collection<Project> starredProjects = projectFactory.getStarredProjects(userZiad);

        when(userService.getAuthenticatedUser()).thenReturn(userZiad);
        when(projectService.getProjects(userZiad)).thenReturn(projects);
        when(projectService.getStarredProjects(userZiad)).thenReturn(starredProjects);

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userZiad))
                .andExpect(model().attribute("projects", projects))
                .andExpect(model().attribute("starredProjects", starredProjects))
                .andExpect(view().name("project/list"));
    }

    @Test
    public void detailsTest() throws Exception {
        String projectIdentifier = "gosh-jiro";
        Collection<Experiment> experiments = new DummyExperimentFactory().getExperimentList(4);

        when(userService.getAuthenticatedUser()).thenReturn(userZiad);
        when(projectService.getProjectByIdentifier(projectIdentifier, true)).thenReturn(projectJiro);
        when(experimentService.getExperimentsByProject(projectIdentifier)).thenReturn(experiments);

        mockMvc.perform(get("/project/{name}", projectIdentifier))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userZiad))
                .andExpect(model().attribute("project", projectJiro))
                .andExpect(model().attribute("experiments", experiments))
                .andExpect(view().name("project/details"));
    }

    @Test
    public void settingsTest() throws Exception {
        String projectIdentifier = "gosh-jiro";
        Collection<DataSource> dataSources = new ArrayList<>();
        Collection<DataPermission> dataPermissions = new ArrayList<>();

        when(userService.getAuthenticatedUser()).thenReturn(userZiad);
        when(projectService.getProjectByIdentifier(projectIdentifier, true)).thenReturn(projectJiro);
        when(pluginService.getDataSources()).thenReturn(dataSources);
        when(dataPermissionService.getAllPermissionsForUser(userZiad)).thenReturn(dataPermissions);

        mockMvc.perform(get("/project/{name}/settings", projectIdentifier))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userZiad))
                .andExpect(model().attribute("project", projectJiro))
                .andExpect(model().attribute("dataSources", new HashMap<String, DataSource>()))
                .andExpect(model().attribute("dataPermissions", dataPermissions))
                .andExpect(view().name("project/settings"));
    }

    @Test
    public void newProjectTest() throws Exception {
        Collection<DataSource> dataSources = new ArrayList<>();
        Collection<DataPermission> dataPermissions = new ArrayList<>();

        when(userService.getAuthenticatedUser()).thenReturn(userZiad);
        when(pluginService.getDataSources()).thenReturn(dataSources);
        when(dataPermissionService.getAllPermissionsForUser(userZiad)).thenReturn(dataPermissions);

        mockMvc.perform(get("/project/new"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userZiad))
                .andExpect(model().attribute("dataPermissions", dataPermissions))
                .andExpect(model().attribute("dataSources", new HashMap<String, DataSource>()))
                .andExpect(view().name("project/new"));
    }

    @Test
    public void changeProjectStarTestStar() throws Exception {
        String typeVal = "Star";
        mockMvc.perform(post("/project/{name}", "project-ident").param("type", typeVal))
                .andExpect(status().isOk());
    }

    @Test
    public void changeProjectStarTestUnstar() throws Exception {
        String typeVal = "Unstar";
        mockMvc.perform(post("/project/{name}", "project-ident").param("type", typeVal))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProjectTest() throws Exception {
        mockMvc.perform(post("/project/{name}/delete", "project-ident"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/projects"));
    }

    @Test
    public void persistNewProjectTest() throws Exception {
        Collection<Integer> users = new ArrayList<>();
        users.add(1);
        users.add(2);
        users.add(3);
        users.add(4);

        when(userService.getAuthenticatedUser()).thenReturn(userZiad);
        when(userService.getUsers(users)).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/project/new")
                .param("name", "Project Name")
                .param("description", "Project description")
                .param("image", "somePathToImage")
                .param("members", "1, 2, 3, 4")
                .param("sources", "someSourceName"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/projects"));
    }

    @Test
    public void persistNewProjectTestWithException() throws Exception {
        Collection<Integer> users = new ArrayList<>();
        users.add(1);

        when(userService.getAuthenticatedUser()).thenReturn(null, userZiad);
        when(userService.getUsers(users)).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/project/new")
                .param("name", "Project Name")
                .param("description", "Project description")
                .param("image", "somePathToImage")
                .param("members", "1")
                .param("sources", "someSourceName"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("error", "The validated object is null"))
                .andExpect(model().attribute("user", userZiad))
                .andExpect(view().name("project/new"));
    }

    @Test
    @Ignore
    public void updateProjectTest() throws Exception {
        Collection<Integer> users = new ArrayList<>();
        users.add(1);
        String projectIdent = "project-jiro";
        Collection<DataProvider> dataProviders = new ArrayList<>();
        when(projectService.getProjectByIdentifier(projectIdent, true)).thenReturn(projectJiro);
        when(pluginService.getDataProviders()).thenReturn(dataProviders);
        when(userService.getAuthenticatedUser()).thenReturn(userZiad);
        when(userService.getUsers(users)).thenReturn(new ArrayList<>());

        mockMvc.perform(post("/project/{ident}/update", projectIdent)
                .param("description", "Project description")
                .param("image", "somePathToImage")
                .param("members", "1")
                .param("sources", "someSourceName"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("error", nullValue()))
                .andExpect(model().attribute("user", userZiad))
                .andExpect(model().attribute("project", projectJiro))
                .andExpect(view().name("project/settings"));
    }

    @Test
    public void updateProjectTestWithException() throws Exception {
        String projectIdent = "project-jiro";
        Collection<DataProvider> dataProviders = new ArrayList<>();
        when(projectService.getProjectByIdentifier(projectIdent, true)).thenReturn(null, projectJiro);
        when(pluginService.getDataProviders()).thenReturn(dataProviders);
        when(userService.getAuthenticatedUser()).thenReturn(userZiad);

        mockMvc.perform(post("/project/{ident}/update", projectIdent)
                .param("description", "New description")
                .param("image", "somePathToImage")
                .param("members", "1")
                .param("sources", "someSourceName"))
                .andDo(print())
                .andExpect(status().is(302))
                .andExpect(flash().attribute("message", "Update failed null"))
                .andExpect(flash().attribute("alertClass", "alert-danger"))
                .andExpect(redirectedUrl("/project/" + projectIdent + "/settings"));
    }

}
