/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.api.user;

import org.springframework.web.bind.annotation.*;
import org.ucl.newton.framework.Credential;
import org.ucl.newton.framework.User;
import org.ucl.newton.framework.UserRole;
import org.ucl.newton.service.authentication.AuthenticationService;
import org.ucl.newton.service.authentication.UnknownRoleException;
import org.ucl.newton.service.user.UserService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Instances of this class expose user service methods via REST.
 *
 * @author Blair Butterworth
 */
@RestController
@SuppressWarnings("unused")
public class UserApi
{
    private UserService userService;
    private AuthenticationService authService;

    @Inject
    public UserApi(UserService userService, AuthenticationService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    public void addUser(@RequestBody UserDto userDto) {
        User user = userService.addUser(userDto);
        authService.save(userDto, user);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public Collection<User> getUsers(@RequestParam(value="matching")String matching) {
        return userService.getUsers(matching);
    }

    @RequestMapping(value = "/api/privilegedusers", method = RequestMethod.GET)
    public Collection<User> getPrivilegedUsers(@RequestParam(value="matching")String matching) {
        Collection<User> matchingUsers = userService.getUsers(matching);
        Collection<User> withPrivilege = new ArrayList<>();
        for(User user: matchingUsers) {
            Credential userCred = authService.getCredentials(user);
            if(userCred.getRole() == UserRole.ADMIN || userCred.getRole() == UserRole.ORGANISATIONLEAD) {
                withPrivilege.add(user);
            }
        }
        return withPrivilege;
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.DELETE)
    public void removeUser(@RequestBody UserDto userDto) {
        User user = userService.getUserByEmail(userDto.getEmail());
        userService.removeUser(user);
    }

    @RequestMapping(value = "/api/userrole", method = RequestMethod.GET)
    public UserRole getUserRole(@RequestParam(value="username") String userName) {
        Credential creds = (Credential)authService.loadUserByUsername(userName);
        return creds.getRole();
    }

    @RequestMapping(value = "/api/updaterole", method = RequestMethod.POST)
    public UserRole getUserRole(@RequestParam(value="username") String userName,
                                @RequestParam(value="role") String role) {
        try {
            return authService.changeRole(userName, role);
        }
        catch (UnknownRoleException e) {
            return null; // Temporary (fix to handle properly)
        }
    }
}
