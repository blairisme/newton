/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.framework.User;
import org.ucl.newton.service.user.UserRepository;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@ActiveProfiles("development")
public class UserRepositoryTest
{
    @Inject
    private UserRepository repository;

    @Test
    public void getUserTest() {
        User expected = new User("user", "$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m", "Test User", "CONTRIBUTOR");
        User actual = repository.getUser("user");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findUserTest() throws Exception{
        User user1 = new User("testa", "password", "John A", "ADMINISTRATOR");
        User user2 = new User("testb", "password", "John B", "ADMINISTRATOR");
        User user3 = new User("testc", "password", "Test User A", "ADMINISTRATOR");

        repository.addUser(user1);
        repository.addUser(user2);
        repository.addUser(user3);

        Collection<User> expected = Arrays.asList(user1, user2);
        Collection<User> actual = repository.findUsers("John");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addUserTest() {
        User expected = new User("test1", "password", "Test User A", "ADMINISTRATOR");
        repository.addUser(expected);
        User actual = repository.getUser("test1");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void removeUserTest() {
        User user = new User("test2", "password", "Test User B", "ADMINISTRATOR");

        repository.addUser(user);
        User before = repository.getUser("test2");
        Assert.assertNotNull(before);

        repository.removeUser(before);
        User after = repository.getUser("test2");
        Assert.assertNull(after);
    }
}
