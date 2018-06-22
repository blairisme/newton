/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.ui.ModelMap;

public class ServletControllerTest
{
    @Test
    public void testRoot(){
        ServletController controller = new ServletController();
        Assert.assertEquals("redirect:/projects", controller.root(new ModelMap()));
    }
}
