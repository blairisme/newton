/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.framework.DataPermission;
import org.ucl.newton.framework.User;
import org.ucl.newton.sdk.processor.DataProcessor;
import org.ucl.newton.sdk.provider.DataProvider;
import org.ucl.newton.sdk.publisher.DataPublisher;
import org.ucl.newton.service.data.DataPermissionService;
import org.ucl.newton.service.plugin.Plugin;
import org.ucl.newton.service.plugin.PluginService;
import org.ucl.newton.service.project.ProjectService;
import org.ucl.newton.service.user.UserService;
import org.ucl.newton.testobjects.DummyProjectFactory;
import org.ucl.newton.testobjects.DummyUserFactory;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SettingsControllerTest
{
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private DataPermissionService dataPermissionService;

    @Mock
    private PluginService pluginService;

    @Mock
    private ProjectService projectService;

    @Mock
    private ApplicationStorage applicationStorage;

    @InjectMocks
    private SettingsController settingsController;

    private User userBlair;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(settingsController)
                .build();
        userBlair = DummyUserFactory.createUserBlair();
    }

    @Test
    public void rolesTest() throws Exception {
        when(userService.getAuthenticatedUser()).thenReturn(userBlair);

        mockMvc.perform(get("/settings/roles"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userBlair))
                .andExpect(view().name("settings/roles"));
    }


    @Test
    public void dataPermissionsTest() throws Exception {
        Collection<DataPermission> ownedPermissions = getOwnedPermissions();
        Collection<DataPermission> grantedPermissions = getGrantedPermissions();

        when(userService.getAuthenticatedUser()).thenReturn(userBlair);
        when(dataPermissionService.getPermissionsOwnedByUser(userBlair)).thenReturn(ownedPermissions);
        when(dataPermissionService.getPermissionsGrantedToUser(userBlair)).thenReturn(grantedPermissions);

        mockMvc.perform(get("/settings/data-permissions"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userBlair))
                .andExpect(model().attribute("ownedPermission", ownedPermissions))
                .andExpect(model().attribute("grantedPermission", grantedPermissions))
                .andExpect(view().name("settings/data-permissions"));
    }

    private Collection<DataPermission> getOwnedPermissions() {
        Collection<DataPermission> ownedPermissions = new ArrayList<>();
        DataPermission permission1 = new DataPermission(1, userBlair, "data-source-1",
                DummyUserFactory.createListOfUsers(2));
        DataPermission permission2 = new DataPermission(2, userBlair, "data-source-2",
                DummyUserFactory.createListOfUsers(4));
        ownedPermissions.add(permission1);
        ownedPermissions.add(permission2);
        return ownedPermissions;
    }

    private Collection<DataPermission> getGrantedPermissions() {
        Collection<DataPermission> grantedPermissions = new ArrayList<>();
        User userZiad = DummyUserFactory.createUserZiad();
        DataPermission permission3 = new DataPermission(3, userZiad, "data-source-3",
                DummyUserFactory.createListOfUsers(3));
        DataPermission permission4 = new DataPermission(4, userZiad, "data-source-4",
                DummyUserFactory.createListOfUsers(2));
        DataPermission permission5 = new DataPermission(5, userZiad, "data-source-5",
                DummyUserFactory.createListOfUsers(3));
        grantedPermissions.add(permission3);
        grantedPermissions.add(permission4);
        grantedPermissions.add(permission5);
        return grantedPermissions;
    }

    @Test
    public void profileTest() throws Exception{
        DummyProjectFactory projectFactory = new DummyProjectFactory();
        User userZiad = DummyUserFactory.createUserZiad();

        when(userService.getAuthenticatedUser()).thenReturn(userZiad);
        when(projectService.getProjects(userZiad)).thenReturn(projectFactory.getProjects(userZiad));
        when(projectService.getOwnedProjects(userZiad)).thenReturn(projectFactory.getProjects(userZiad));
        when(projectService.getStarredProjects(userZiad)).thenReturn(projectFactory.getStarredProjects(userZiad));

         mockMvc.perform(get("/profile"))
                 .andExpect(status().isOk())
                 .andExpect(model().attribute("user", userZiad))
                 .andExpect(model().attribute("numProjectsAMemberOf", projectFactory.getProjects(userZiad).size()))
                 .andExpect(model().attribute("numOfProjectsOwned", projectFactory.getProjects(userZiad).size()))
                 .andExpect(model().attribute("numOfProjectsStarred",
                         projectFactory.getStarredProjects(userZiad).size()))
                 .andExpect(view().name("settings/profile"));
    }

    @Test
    public void deleteUserTest() throws Exception {
        mockMvc.perform(post("/profile/delete"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/logout"));
    }

    @Test
    public void deleteUserExceptionTest() throws Exception {
        when(userService.getAuthenticatedUser()).thenThrow(new NullPointerException());

        mockMvc.perform(post("/profile/delete"))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/profile"));
    }

    @Test
    public void deletePluginTest() throws Exception {
        String pluginId = "pluginId";

        when(pluginService.getPlugin(pluginId)).thenReturn(new Plugin("somelocation"));

        mockMvc.perform(get("/settings/plugins/delete")
                .param("pluginId", pluginId))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/settings/plugins"));
    }

    @Test
    public void updatePluginsTest() throws Exception {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.put("key1", new ArrayList<>());

        mockMvc.perform(post("/settings/plugins/update")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED).content(EntityUtils.toString(
                        new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("formData", formData.toString()))))))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/settings/plugins"));
    }

    @Test
    public void addPluginTest() throws Exception {
        MockMultipartFile multiPart = new MockMultipartFile("data", "filename.txt",
                "text/plain", "some xml".getBytes());

        when(applicationStorage.getPluginsDirectory()).thenReturn(Paths.get(""));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/settings/plugins/add")
                .file("pluginData", multiPart.getBytes()))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/settings/plugins"));

        File folder = new File(".");
        for (File f : folder.listFiles()) {
            if (f.getName().endsWith(".jar")) {
                f.delete();
            }
        }

    }

    @Test
    public void viewPluginsTest() throws Exception {
        Collection<DataProvider> providers = new ArrayList<>();
        Collection<DataProcessor> processors = new ArrayList<>();
        Collection<DataPublisher> publishers = new ArrayList<>();

        when(userService.getAuthenticatedUser()).thenReturn(userBlair);
        when(pluginService.getDataProviders()).thenReturn(providers);
        when(pluginService.getDataProcessors()).thenReturn(processors);
        when(pluginService.getDataPublishers()).thenReturn(publishers);

        mockMvc.perform(get("/settings/plugins"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("user", userBlair))
                .andExpect(model().attribute("providers", providers))
                .andExpect(model().attribute("processors", processors))
                .andExpect(model().attribute("publishers", publishers))
                .andExpect(view().name("settings/plugins"));
    }
}
