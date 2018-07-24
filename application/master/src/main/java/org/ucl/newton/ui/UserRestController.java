/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ucl.newton.framework.Credential;
import org.ucl.newton.framework.User;
import org.ucl.newton.framework.UserRole;
import org.ucl.newton.service.authentication.AuthenticationService;
import org.ucl.newton.service.authentication.UnknownRoleException;
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
    private AuthenticationService authService;

    @Inject
    public UserRestController(UserService userService, AuthenticationService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public Collection<User> findUsers(@RequestParam(value="matching")String matching) {
        return userService.findUsers(matching);
    }

    @RequestMapping(value = "/api/userrole", method = RequestMethod.GET)
    public UserRole getUserRole(@RequestParam(value="username") String userName) {
        Credential creds = (Credential) authService.loadUserByUsername(userName); // fix this to not use cast
        return creds.getRole();
    }

    @RequestMapping(value = "/api/updaterole", method = RequestMethod.POST)
    public UserRole getUserRole(@RequestParam(value="username") String userName,
                                @RequestParam(value="role") String role) {
        try {
            return authService.changeRole(userName, role);
        } catch (UnknownRoleException e) {
            return null; // Temporary (fix to handle properly)
        }
    }
}
