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
import javax.persistence.NoResultException;
import java.util.Arrays;
import java.util.Collection;

@ActiveProfiles("development")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
public class UserRepositoryTest
{
    @Inject
    private org.ucl.newton.service.user.UserRepository repository;

    @Test
    public void getUserTest() {
        User expected = new User(1, "user", "user@ucl.ac.uk", "default.jpg");
        User actual = repository.getUser(1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findUserTest() throws Exception{
        User user1 = new User("Foo A", "foo.a@ucl.ac.uk", "default.jpg");
        User user2 = new User("Foo B", "foo.b@ucl.ac.uk", "default.jpg");
        User user3 = new User("Bar A", "bar.a@ucl.ac.uk", "default.jpg");
        User user4 = new User("Bar B", "bar.b@ucl.ac.uk", "default.jpg");

        repository.addUser(user1);
        repository.addUser(user2);
        repository.addUser(user3);
        repository.addUser(user4);

        Collection<User> expected = Arrays.asList(user1, user2);
        Collection<User> actual = repository.getUsers("Foo");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addUserTest() {
        User expected = new User("test1", "test1@ucl.ac.uk","default.jpg");
        repository.addUser(expected);

        User actual = repository.getUser(expected.getId());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void removeUserTest() {
        User user = new User("test2", "test2@ucl.ac.uk", "default.jpg");
        repository.addUser(user);

        User before = repository.getUser(user.getId());
        Assert.assertNotNull(before);

        repository.removeUser(before);
        User after = repository.getUser(user.getId());
        Assert.assertNull(after);
    }

    @Test
    public void getUserByEmailInDb() {
        User adminUser = repository.getUserByEmail("admin@ucl.ac.uk");
        Assert.assertNotNull(adminUser);
    }

    @Test
    public void getUserByEmailNotInDb() {
        try {
            User userNotInDb = repository.getUserByEmail("someEmail@ucl.ac.uk");
        } catch(NoResultException e) {
            Assert.assertTrue(true);
        }
    }
}
