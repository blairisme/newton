/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.authentication;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.newton.framework.Credential;

public class AuthenticationServiceTest
{
    @Test
    public void loadUserByUsernameTest() {
        Credential expected = Mockito.mock(Credential.class);

        CredentialRepository repository = Mockito.mock(CredentialRepository.class);
        Mockito.when(repository.getCredentialByName("test")).thenReturn(expected);

        AuthenticationService authenticationService = new AuthenticationService(repository);
        Credential actual = (Credential)authenticationService.loadUserByUsername("test");

        Assert.assertEquals(expected, actual);
    }
}
