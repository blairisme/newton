/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.webapp;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.ucl.newton.common.SystemUtils;

import java.nio.file.Path;

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

        ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", new DispatcherServlet(context));
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
        servlet.setInitParameter("spring.profiles.active", getProfile());



        Path indexPath = SystemUtils.newTempDirectory("developer/uploads");
        int MAX_UPLOAD_SIZE = 5 * 1024 * 1024;
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(indexPath.toString(),
                MAX_UPLOAD_SIZE, MAX_UPLOAD_SIZE * 2, MAX_UPLOAD_SIZE / 2);

        servlet.setMultipartConfig(multipartConfigElement);
    }

    private String getProfile() {
        String profile = System.getProperty("run.profile");
        if (profile == null) {
            profile = "production";
        }
        return profile;
    }
}