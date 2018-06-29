/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.user;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.framework.User;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;

@ActiveProfiles("development")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
public class UserRepositoryTest
{
    @Inject
    private UserRepository repository;

    @Test
    public void getUserTest() {
        User expected = new User(0, "user", "user@ucl.ac.uk");
        User actual = repository.getUser(0);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findUserTest() throws Exception{
        User user1 = new User("John A", "john.a@ucl.ac.uk");
        User user2 = new User("John B", "john.b@ucl.ac.uk");
        User user3 = new User("Blair A", "blair.butterworth.a@ucl.ac.uk");
        User user4 = new User("Blair B", "blair.butterworth.b@ucl.ac.uk");

        repository.addUser(user1);
        repository.addUser(user2);
        repository.addUser(user3);
        repository.addUser(user4);

        Collection<User> expected = Arrays.asList(user1, user2);
        Collection<User> actual = repository.findUsers("John");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addUserTest() {
        User expected = new User("test1", "test1@ucl.ac.uk");
        repository.addUser(expected);

        User actual = repository.getUser(expected.getId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void removeUserTest() {
        User user = new User("test2", "test2@ucl.ac.uk");
        repository.addUser(user);

        User before = repository.getUser(user.getId());
        Assert.assertNotNull(before);

        repository.removeUser(before);
        User after = repository.getUser(user.getId());
        Assert.assertNull(after);
    }
}
