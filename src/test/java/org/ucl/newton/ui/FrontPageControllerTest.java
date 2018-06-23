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

public class FrontPageControllerTest
{
    @Test
    public void frontPageTest(){
        FrontPageController controller = new FrontPageController();
        Assert.assertEquals("Front", controller.frontPage(new ModelMap()));
    }
}
