/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.network;

import com.mashape.unirest.request.HttpRequest;
import org.ucl.newton.common.serialization.Serializer;

import javax.inject.Inject;

/**
 * Instances of this class represent a representational state transfer (REST)
 * web service made to an authenticated endpoint.
 *
 * @author Blair Butterworth
 */
public class AuthenticatedRestServer extends RestServer
{
    private boolean authenticate;
    private AuthenticationStrategy authenticationStrategy;

    @Inject
    @SuppressWarnings("unused")
    public AuthenticatedRestServer() {
        this.authenticate = true;
    }

    protected AuthenticatedRestServer(AuthenticatedRestServer another, boolean authenticate) {
        super(another);
        this.authenticate = authenticate;
    }

    public void setAuthenticationStrategy(AuthenticationStrategy authenticationStrategy) {
        this.authenticationStrategy = authenticationStrategy;
    }

    @Override
    protected RestRequest newRestRequest(HttpRequest request, Serializer serializer) {
        if (authenticate) {
            AuthenticatedRestServer unauthenticatedServer = new AuthenticatedRestServer(this, false);
            return new AuthenticatedRestRequest(unauthenticatedServer, authenticationStrategy, request, serializer);
        }
        return super.newRestRequest(request, serializer);
    }
}
