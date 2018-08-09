/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.network;

import org.apache.commons.lang3.Validate;
import org.apache.http.HttpHeaders;

/**
 * An {@link AuthenticationStrategy} that authenticates REST requests using
 * HTTP basic authentication, a header attribute containing a BASE 64 encoded
 * username and password.
 *
 * @author Blair Butterworth
 */
public class BasicAuthentication implements AuthenticationStrategy
{
    private String username;
    private String password;

    public BasicAuthentication(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public void authenticate(RestServer server, RestRequest request) {
        updateAuthorizationHeader(server, request);
    }

    @Override
    public void reauthenticate(RestServer server, RestRequest request) {
        updateAuthorizationHeader(server, request);
    }

    private void updateAuthorizationHeader(RestServer server, RestRequest request) {
        Validate.notNull(username);
        Validate.notNull(password);

        String authorization = BasicEncoder.encode(username, password);
        server.setHeader(HttpHeaders.AUTHORIZATION, authorization);
        request.setHeader(HttpHeaders.AUTHORIZATION, authorization);
    }
}
