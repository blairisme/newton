/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.application;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Unit tests for the {@link ApplicationConfiguration} class.
 *
 * @author Blair Butterworth
 */
public class ApplicationConfigurationTest
{
    @Test
    public void addResourceHandlersTest() {
        Path rootDirectory = Paths.get("/test/foo");
        ApplicationStorage storage = new ApplicationStorage(rootDirectory);
        ApplicationConfiguration configuration = new ApplicationConfiguration(storage);

        ResourceHandlerRegistry registry = Mockito.mock(ResourceHandlerRegistry.class);
        ResourceHandlerRegistration registration = Mockito.mock(ResourceHandlerRegistration.class);
        Mockito.when(registry.addResourceHandler(Mockito.any())).thenReturn(registration);

        configuration.addResourceHandlers(registry);

        Mockito.verify(registry).addResourceHandler("/files/**");

        if (SystemUtils.IS_OS_WINDOWS) {
            Mockito.verify(registration).addResourceLocations("file:" + rootDirectory.toString() + "/");
        }
        else {
            Mockito.verify(registration).addResourceLocations("file://" + rootDirectory.toString() + "/");
        }
    }
}
