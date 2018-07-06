/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.ucl.newton.framework.Credential;
import org.ucl.newton.framework.User;

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
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Credential credentials = (Credential)authentication.getPrincipal();
        return repository.getUser(credentials.getUserId());
    }

    public Collection<User> findUsers(String matching) {
        return repository.findUsers(matching);
    }

    public User getUser(int identifier) {
        return repository.getUser(identifier);
    }

    public Collection<User> getUsers(Collection<Integer> identifiers) {
        Collection<User> result = new ArrayList<>();
        for (Integer identifier: identifiers) {
            result.add(getUser(identifier));
        }
        return result;
    }
}