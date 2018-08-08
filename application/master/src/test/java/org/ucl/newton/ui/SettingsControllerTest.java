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
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;
import org.ucl.newton.service.user.UserService;

public class SettingsControllerTest
{
    @Test
    public void systemSettingsRolesTest(){
        UserService userService = Mockito.mock(UserService.class);
        SettingsController controller = new SettingsController(userService);
        Assert.assertEquals("settings/roles", controller.roles(new ModelMap()));
    }

    @Test
    public void systemSettingsDataPermissionsTest(){
        UserService userService = Mockito.mock(UserService.class);
        SettingsController controller = new SettingsController(userService);
        Assert.assertEquals("settings/data-permissions", controller.dataPermissions(new ModelMap()));
    }

    @Test
    public void systemSettingsPluginsTest(){
        UserService userService = Mockito.mock(UserService.class);
        SettingsController controller = new SettingsController(userService);
        Assert.assertEquals("settings/plugins", controller.plugins(new ModelMap()));
    }

    @Test
    public void profileTest(){
        UserService userService = Mockito.mock(UserService.class);
        SettingsController controller = new SettingsController(userService);
        Assert.assertEquals("settings/profile", controller.profile(new ModelMap()));
    }
}
