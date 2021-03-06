/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.webapp;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.ucl.newton.application.system.ApplicationStorage;
import org.ucl.newton.common.lang.JarClassLoader;

import javax.inject.Inject;

/**
 * Instances of this class configure the Spring MVC servlet engine, setting the
 * location of the JSP files and resources used by the Newton user
 * interface.
 *
 * @author Blair Butterworth
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.ucl.newton"})
@SuppressWarnings("unused")
public class ApplicationConfiguration implements WebMvcConfigurer, ApplicationContextAware
{
    private ApplicationContext applicationContext;
    private ApplicationStorage applicationStorage;

    @Inject
    public ApplicationConfiguration(ApplicationStorage applicationStorage) {
        this.applicationStorage = applicationStorage;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/", "file:" + applicationStorage.getRootPath());
        registry.addResourceHandler(".well-known/acme-challenge/**")
                .addResourceLocations("/acme-challenge/");
    }

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Bean
    public ISpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setEnableSpringELCompiler(true);
        engine.addTemplateResolver(viewTemplateResolver());
        engine.addTemplateResolver(pluginTemplateResolver());
        engine.addDialect(new SpringSecurityDialect());
        return engine;
    }

    private ITemplateResolver viewTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setOrder(1);
        resolver.setCheckExistence(true);
        return resolver;
    }

    private ITemplateResolver pluginTemplateResolver() {
        JarClassLoader classLoader = JarClassLoader.getSystemClassLoader();
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver(classLoader);
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setPrefix("/");
        resolver.setSuffix(".html");
        resolver.setOrder(2);
        return resolver;
    }

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
