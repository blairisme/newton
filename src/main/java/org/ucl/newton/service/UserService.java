/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service;

import org.ucl.newton.framework.User;

import javax.inject.Named;

/**
 * Instances of this interface provide access to user data.
 *
 * @author Blair Butterworth
 */
@Named
public class UserService
{
    private User authenticatedUser;

    public UserService() {
        this.authenticatedUser = new User("Blair");
    }

    public User getAuthenticatedUser() {
        //SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return authenticatedUser;
    }
}
