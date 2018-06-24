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

public class ApplicationConfigurationTest
{
    @Test
    public void testTemplateEngine(){
        ApplicationConfiguration configuration = new ApplicationConfiguration();
        Assert.assertNotNull(configuration.templateEngine());
    }

    @Test
    public void testViewResolver(){
        ApplicationConfiguration configuration = new ApplicationConfiguration();
        Assert.assertNotNull(configuration.viewResolver());
    }
}
