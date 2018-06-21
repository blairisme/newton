package org.ucl.newton.application;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class ServletInitializerTest
{
    @Test
    public void testInitializer(){
        ServletContext context = Mockito.mock(ServletContext.class);
        ServletRegistration.Dynamic registration = Mockito.mock(ServletRegistration.Dynamic.class);
        Mockito.when(context.addServlet(Mockito.any(), Mockito.any(DispatcherServlet.class))).thenReturn(registration);

        ServletInitializer initializer = new ServletInitializer();
        initializer.onStartup(context);
    }
}