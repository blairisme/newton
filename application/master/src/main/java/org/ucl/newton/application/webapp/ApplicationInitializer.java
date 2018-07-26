/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.webapp;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.ucl.newton.application.system.ApplicationPreferences;
import org.ucl.newton.application.system.ApplicationPreferencesDefault;
import org.ucl.newton.application.system.ApplicationPreferencesUser;

import javax.inject.Inject;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

/**
 * Instances of this class configure the Spring MVC servlet engine used to
 * render the Newton user interface.
 *
 * @author Blair Butterworth
 */
@SuppressWarnings("unused")
public class ApplicationInitializer implements WebApplicationInitializer
{
    public void onStartup(ServletContext container) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(ApplicationConfiguration.class);
        context.setServletContext(container);

        ApplicationPreferences applicationPreferences = new ApplicationPreferencesUser(new ApplicationPreferencesDefault());
        MultipartConfigElement multipartConfig = new MultipartConfigElement(
            applicationPreferences.getUploadDirectory(),
            applicationPreferences.getUploadMaximumSize(),
            applicationPreferences.getUploadMaximumSize() * 2,
            applicationPreferences.getUploadMaximumSize() / 2);

        ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(context));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
        servlet.setInitParameter("spring.profiles.active", applicationPreferences.getProfile());
        servlet.setMultipartConfig(multipartConfig);
    }
}