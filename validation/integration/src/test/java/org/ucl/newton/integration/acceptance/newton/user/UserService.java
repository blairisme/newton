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

    public void addUsers(Collection<User> users) throws RestException {
        for (User user: users) {
            RestRequest request = server.post(UserResource.User);
            request.setBody(user, User.class);
            request.make();
        }
    }

    public void removeUsers() throws RestException {
        RestRequest request = server.delete(UserResource.Users);
        request.make();
    }
}
