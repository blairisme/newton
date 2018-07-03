/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ucl.newton.framework.User;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;
import java.util.Collection;

/**
 * Instances of this class expose user service methods via REST.
 *
 * @author Blair Butterworth
 */
@RestController
public class UserRestController
{
    private UserService userService;

    @Inject
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public Collection<User> findUsers(@RequestParam(value="matching")String matching) {
        return userService.findUsers(matching);
    }
}
