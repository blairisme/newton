/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.ucl.newton.service.data.DataPermissionService;
import org.ucl.newton.service.plugin.PluginService;
import org.ucl.newton.service.project.ProjectService;
import org.ucl.newton.service.user.UserService;

public class SettingsControllerTest
{
    private UserService userService;
    private DataPermissionService dataPermissionService;
    private PluginService pluginService;
    private ProjectService projectService;
    private SettingsController controller;

    @Before
    public void setUp() {
        userService = Mockito.mock(UserService.class);
        dataPermissionService = Mockito.mock(DataPermissionService.class);
        pluginService = Mockito.mock(PluginService.class);
        projectService = Mockito.mock(ProjectService.class);
        controller = new SettingsController(userService, dataPermissionService, pluginService, projectService);
    }

    @Test
    public void systemSettingsRolesTest() {
        Assert.assertEquals("settings/roles", controller.roles(new ModelMap()));
    }

    @Test
    public void systemSettingsDataPermissionsTest() {
        Assert.assertEquals("settings/data-permissions", controller.dataPermissions(new ModelMap()));
    }

    @Test
    public void systemSettingsPluginsTest() {
        Assert.assertEquals("settings/plugins", controller.viewPlugins(new ModelMap()));
    }

    @Test
    public void profileTest() {
        Assert.assertEquals("settings/profile", controller.profile(new ModelMap()));
    }

    @Test
    public void updatePluginTest() {
        Assert.assertEquals("redirect:/settings/plugins", controller.updatePlugins(new LinkedMultiValueMap<>()));
    }

    @Test
    public void deleteUserFailingTest() {
        Assert.assertEquals("redirect:/logout", controller.deleteUser());
    }
}
