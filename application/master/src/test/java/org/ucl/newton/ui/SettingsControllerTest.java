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
import org.ucl.newton.service.permission.PermissionService;
import org.ucl.newton.service.plugin.PluginService;
import org.ucl.newton.service.user.UserService;

public class SettingsControllerTest
{
    private UserService userService;
    private PermissionService permissionService;
    private PluginService pluginService;

    @Before
    public void setUp() {
        userService = Mockito.mock(UserService.class);
        permissionService = Mockito.mock(PermissionService.class);
        pluginService = Mockito.mock(PluginService.class);
    }

    @Test
    public void systemSettingsRolesTest(){
        SettingsController controller = new SettingsController(userService, permissionService, pluginService);
        Assert.assertEquals("settings/roles", controller.roles(new ModelMap()));
    }

    @Test
    public void systemSettingsDataPermissionsTest(){
        SettingsController controller = new SettingsController(userService, permissionService, pluginService);
        Assert.assertEquals("settings/data-permissions", controller.dataPermissions(new ModelMap()));
    }

    @Test
    public void systemSettingsPluginsTest(){
        SettingsController controller = new SettingsController(userService, permissionService, pluginService);
        Assert.assertEquals("settings/plugins", controller.viewPlugins(new ModelMap()));
    }

    @Test
    public void profileTest(){
        SettingsController controller = new SettingsController(userService, permissionService, pluginService);
        Assert.assertEquals("settings/profile", controller.profile(new ModelMap()));
    }
}
