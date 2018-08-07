package org.ucl.newton.integration.acceptance.newton.user;

import org.ucl.newton.common.network.*;

import java.util.Collection;

public class UserService
{
    private RestServer server;

    public UserService(RestServer server) {
        this.server = server;
    }

    public void addUsers(Collection<User> users) throws RestException {
        for (User user: users) {
            RestRequest request = server.post("user");
            request.setBody(user, User.class);
            request.make();
        }
    }

    public void removeUsers() throws RestException {
        RestRequest request = server.delete("users");
        RestResponse response = request.make();
        response.isEmpty();
    }
}
