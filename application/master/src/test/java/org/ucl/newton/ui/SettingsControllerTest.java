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
    public void systemSettingsTest(){
        UserService userService = Mockito.mock(UserService.class);
        SettingsController controller = new SettingsController(userService);
        Assert.assertEquals("settings/system", controller.settings(new ModelMap()));
    }

    @Test
    public void profileTest(){
        UserService userService = Mockito.mock(UserService.class);
        SettingsController controller = new SettingsController(userService);
        Assert.assertEquals("settings/profile", controller.profile(new ModelMap()));
    }
}
