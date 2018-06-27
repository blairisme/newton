/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.security;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class SecurityConfigurationTest
{
    @Test
    public void passwordEncoderTest() {
        SecurityConfiguration configuration = new SecurityConfiguration();
        PasswordEncoder passwordEncoder = configuration.passwordEncoder();
        Assert.assertNotNull(passwordEncoder);
    }

    @Test
    public void authenticationProviderTest() {
        SecurityConfiguration configuration = new SecurityConfiguration();
        DaoAuthenticationProvider authenticationProvider = configuration.authenticationProvider();
        Assert.assertNotNull(authenticationProvider);
    }

    @Test
    public void configureTest() throws Exception {
        HttpSecurity httpSecurity = newSecurity();
        SecurityConfiguration configuration = new SecurityConfiguration();
        configuration.configure(httpSecurity);
    }

    @SuppressWarnings("unchecked")
    private HttpSecurity newSecurity() {
        ObjectPostProcessor<Object> objectPostProcessor = Mockito.mock(ObjectPostProcessor.class);
        AuthenticationManagerBuilder authenticationBuilder = Mockito.mock(AuthenticationManagerBuilder.class);
        Map<Class<? extends Object>, Object> sharedObjects = new HashMap<>();
        return new HttpSecurity(objectPostProcessor, authenticationBuilder, sharedObjects);
    }
}
