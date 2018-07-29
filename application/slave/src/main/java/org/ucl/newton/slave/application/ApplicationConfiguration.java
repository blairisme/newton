/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * Configures resource paths for the slave web application.
 *
 * @author Ziad Halabi
 * @author Blair Butterworth
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.ucl.newton"})
@SuppressWarnings("unused")
public class ApplicationConfiguration implements WebMvcConfigurer
{
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String path = new File("").getAbsolutePath();
        registry.addResourceHandler("/files/**").addResourceLocations("file://" + path +"/");
    }
}
