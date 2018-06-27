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
import org.mockito.Mockito;
import org.ucl.newton.framework.User;
import org.ucl.newton.service.user.UserRepository;
import org.ucl.newton.service.user.UserService;

public class UserServiceTest
{
    @Test
    public void loadUserByUsernameTest() {
        User expected = Mockito.mock(User.class);

        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.getUser("test")).thenReturn(expected);

        UserService userService = new UserService(userRepository);
        User actual = (User)userService.loadUserByUsername("test");

        Assert.assertEquals(expected, actual);
    }
}
