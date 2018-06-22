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

public class ServletConfigurationTest
{
    @Test
    public void testTemplateEngine(){
        ServletConfiguration configuration = new ServletConfiguration();
        Assert.assertNotNull(configuration.templateEngine());
    }

    @Test
    public void testViewResolver(){
        ServletConfiguration configuration = new ServletConfiguration();
        Assert.assertNotNull(configuration.viewResolver());
    }
}
