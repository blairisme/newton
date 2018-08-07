/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.user;

import org.apache.commons.lang3.Validate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.ucl.newton.framework.Credential;
import org.ucl.newton.framework.User;
import org.ucl.newton.framework.UserDto;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Instances of this interface provide access to user data.
 *
 * @author Blair Butterworth
 */
@Named
public class UserService
{
    private UserRepository repository;

    @Inject
    public UserService(UserRepository repository)
    {
        this.repository = repository;
    }

    public User addUser(UserDto user){
        Validate.notNull(user);
        return addUser(new User(user.getFullName(), user.getEmail(), "default.jpg"));
    }

    public User addUser(User user) {
        Validate.notNull(user);
        return repository.addUser(user);
    }

    public User getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principle = authentication.getPrincipal();

        if (principle instanceof Credential) {
            Credential credentials = (Credential) authentication.getPrincipal();
            return repository.getUser(credentials.getUserId());
        }
        return new User("Anonymous", "anonymous@ucl.ac.uk", "default.jpg");
    }

    public User getUser(int identifier) {
        return repository.getUser(identifier);
    }

    public User getUserByEmail(String emailAddress){
        Validate.notNull(emailAddress);
        return repository.getUserByEmail(emailAddress);
    }

    public Collection<User> getUsers() {
        return repository.getUsers();
    }

    public Collection<User> getUsers(String matching) {
        Validate.notNull(matching);
        return repository.getUsers(matching);
    }

    public Collection<User> getUsers(Collection<Integer> identifiers) {
        Validate.notNull(identifiers);
        Collection<User> result = new ArrayList<>();
        for (Integer identifier: identifiers) {
            result.add(getUser(identifier));
        }
        return result;
    }

    public void removeUser(User user) {
        Validate.notNull(user);
        repository.removeUser(user);
    }
}
