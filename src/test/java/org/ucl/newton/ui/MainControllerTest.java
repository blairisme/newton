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

public class MainControllerTest
{
    @Test
    public void frontPageTest(){
        MainController controller = new MainController();
        Assert.assertEquals("main/landing", controller.main(new ModelMap()));
    }
}
