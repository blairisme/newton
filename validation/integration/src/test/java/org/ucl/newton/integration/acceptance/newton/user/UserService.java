/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.integration.acceptance.newton.user;

import org.ucl.newton.common.network.RestException;
import org.ucl.newton.common.network.RestRequest;
import org.ucl.newton.common.network.RestServer;

import java.util.Collection;

/**
 * Provides access to user services the Newton REST API.
 *
 * @author Blair Butterworth
 */
public class UserService
{
    private RestServer server;

    public UserService(RestServer server) {
        this.server = server;
    }

    public void addUser(UserDto user) throws RestException {
        RestRequest request = server.post(UserResource.User);
        request.setBody(user, UserDto.class);
        request.make();
    }

    public void addUsers(Collection<UserDto> users) throws RestException {
        for (UserDto user: users) {
            addUser(user);
        }
    }

    public void removeUser(UserDto user) throws RestException {
        RestRequest request = server.delete(UserResource.User);
        request.setBody(user, UserDto.class);
        request.make();
    }

    public void removeUsers(Collection<UserDto> users) throws RestException {
        for (UserDto user: users) {
            removeUser(user);
        }
    }
}
