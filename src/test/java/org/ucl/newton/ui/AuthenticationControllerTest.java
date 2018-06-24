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
import org.springframework.ui.ModelMap;

public class AuthenticationControllerTest
{
    @Test
    public void frontPageTest(){
        AuthenticationController controller = new AuthenticationController();
        Assert.assertEquals("auth/login", controller.login(false, new ModelMap()));
    }
}
