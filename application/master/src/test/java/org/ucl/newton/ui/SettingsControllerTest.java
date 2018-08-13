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

    @Before
    public void setUp() {
        userService = Mockito.mock(UserService.class);
        dataPermissionService = Mockito.mock(DataPermissionService.class);
        pluginService = Mockito.mock(PluginService.class);
        projectService = Mockito.mock(ProjectService.class);
    }

    @Test
    public void systemSettingsRolesTest(){
        SettingsController controller = new SettingsController(userService, dataPermissionService, pluginService, projectService);
        Assert.assertEquals("settings/roles", controller.roles(new ModelMap()));
    }

    @Test
    public void systemSettingsDataPermissionsTest(){
        SettingsController controller = new SettingsController(userService, dataPermissionService, pluginService, projectService);
        Assert.assertEquals("settings/data-permissions", controller.dataPermissions(new ModelMap()));
    }

    @Test
    public void systemSettingsPluginsTest(){
        SettingsController controller = new SettingsController(userService, dataPermissionService, pluginService, projectService);
        Assert.assertEquals("settings/plugins", controller.viewPlugins(new ModelMap()));
    }

    @Test
    public void profileTest(){
        SettingsController controller = new SettingsController(userService, dataPermissionService, pluginService, projectService);
        Assert.assertEquals("settings/profile", controller.profile(new ModelMap()));
    }

    @Test
    public void deleteUserFailingTest(){
        SettingsController controller = new SettingsController(userService, dataPermissionService, pluginService, projectService);
        Assert.assertEquals("redirect:/profile", controller.deleteUser());
    }
}
