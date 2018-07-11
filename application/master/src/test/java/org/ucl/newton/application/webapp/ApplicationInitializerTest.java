/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.webapp;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class ApplicationInitializerTest
{
    @Test
    public void testInitializer(){
        ServletContext context = Mockito.mock(ServletContext.class);
        ServletRegistration.Dynamic registration = Mockito.mock(ServletRegistration.Dynamic.class);
        Mockito.when(context.addServlet(Mockito.any(), Mockito.any(DispatcherServlet.class))).thenReturn(registration);

        ApplicationInitializer initializer = new ApplicationInitializer();
        initializer.onStartup(context);
    }
}
