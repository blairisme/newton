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
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.framework.Credential;
import org.ucl.newton.framework.UserRole;

import javax.inject.Inject;
import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@ActiveProfiles("development")
public class AuthenticationServiceTest
{

    @Inject
    private CredentialRepository repository;
    private AuthenticationService service;

    @Before
    public void setUp() {
        service = new AuthenticationService(repository);
    }

    @Test
    public void loadUserByUsernameTest() {
        Credential expected = Mockito.mock(Credential.class);

        CredentialRepository repository = Mockito.mock(CredentialRepository.class);
        Mockito.when(repository.getCredentialByName("test")).thenReturn(expected);

        AuthenticationService authenticationService = new AuthenticationService(repository);
        Credential actual = (Credential)authenticationService.loadUserByUsername("test");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testChangeCredentialRole(){
        try {
            String userName = "user@ucl.ac.uk";
            UserDetails old = service.loadUserByUsername(userName);
            SimpleGrantedAuthority userRole = new SimpleGrantedAuthority("ROLE_USER");
            SimpleGrantedAuthority adminRole = new SimpleGrantedAuthority("ROLE_ADMIN");
            Assert.assertTrue(old.getAuthorities().contains(userRole));
            Assert.assertFalse(old.getAuthorities().contains(adminRole));
            service.changeRole(userName, "admin");
            UserDetails updatedCreds = service.loadUserByUsername(userName);
            Assert.assertFalse(updatedCreds.getAuthorities().contains(userRole));
            Assert.assertTrue(updatedCreds.getAuthorities().contains(adminRole));
        } catch (UnknownRoleException e) {
            Assert.assertFalse(true);
        }

    }

    @Test(expected = UsernameNotFoundException.class)
    public void testChangeCredentialRoleUnknownUser() throws UnknownRoleException {
        String userName = "user55@ucl.ac.uk";
        service.changeRole(userName, "admin");
    }

    @Test (expected = UnknownRoleException.class)
    public void testChangeCredentialRoleUnknownRole() throws UnknownRoleException {
        String userName = "user@ucl.ac.uk";
        String fakeRole = "admin55";
        service.changeRole(userName, fakeRole);
    }
}
