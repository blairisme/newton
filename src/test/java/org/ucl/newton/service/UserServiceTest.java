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
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.ucl.newton.framework.User;

public class UserServiceTest
{
//    @Test
//    public void getAuthenticatedUserTest() {
//        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
//        SecurityContextHolder.setContext(securityContext);
//
//
//    }

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
