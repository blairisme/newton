/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.webapp;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.newton.application.webapp.ApplicationConfiguration;

public class ApplicationConfigurationTest
{
    @Test
    public void testTemplateEngine(){
        ApplicationStorage applicationStorage = Mockito.mock(ApplicationStorage.class);
        ApplicationConfiguration configuration = new ApplicationConfiguration(applicationStorage);
        Assert.assertNotNull(configuration.templateEngine());
    }

    @Test
    public void testViewResolver(){
        ApplicationStorage applicationStorage = Mockito.mock(ApplicationStorage.class);
        ApplicationConfiguration configuration = new ApplicationConfiguration(applicationStorage);
        Assert.assertNotNull(configuration.viewResolver());
    }
}
