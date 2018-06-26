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

import javax.inject.Inject;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@Transactional
@ActiveProfiles("development")
public class UserRepositoryTest
{
    @Inject
    private UserRepository repository;

    @Test
    public void saveTest() {
        User expected = new User(1l, "blair", "password", "Blair Butterworth", "ADMINISTRATOR");
        //repository.save(expected);
        User actual = repository.read(1L);
        //List<User> users = repository.getAll();
        //User actual = users.get(0);
        Assert.assertEquals(expected, actual);

    }
}
