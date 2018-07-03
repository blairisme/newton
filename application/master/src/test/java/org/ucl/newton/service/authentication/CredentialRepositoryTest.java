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
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.framework.Credential;
import org.ucl.newton.framework.User;

import javax.inject.Inject;

@ActiveProfiles("development")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
public class CredentialRepositoryTest
{
    @Inject
    private CredentialRepository repository;

    @Test
    public void getCredentialByIdTest() {
        Credential expected = new Credential(1, 1, "user", "$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m");
        Credential actual = repository.getCredentialById(1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getCredentialByUsernameTest() {
        Credential expected = new Credential(1, 1, "user", "$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m");
        Credential actual = repository.getCredentialByName("user");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addCredentialTest() {
        Credential expected = new Credential(2, 2, "user1", "$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m");
        repository.addCredential(expected);

        Credential actual = repository.getCredentialById(expected.getId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void removeCredentialTest() {
        Credential credential = new Credential(3, 3, "user2", "$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m");
        repository.addCredential(credential);

        Credential before = repository.getCredentialById(credential.getId());
        Assert.assertNotNull(before);

        repository.removeCredential(before);
        Credential after = repository.getCredentialById(credential.getId());
        Assert.assertNull(after);
    }
}
