/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.application;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

/**
 * Unit tests for the {@link ApplicationSecurity} class.
 *
 * @author Blair Butterworth
 */
public class ApplicationSecurityTest
{
    @Test
    public void authenticationProviderTest() {
        ApplicationSecurity applicationSecurity = new ApplicationSecurity();
        DaoAuthenticationProvider provider = applicationSecurity.authenticationProvider();
        Assert.assertNotNull(provider);
    }
}