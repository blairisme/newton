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

/**
 * Instances of this class represent a request to an authenticated REST service.
 * Methods exist to set the query parameters and body of the request as well as
 * to make the request itself. Authentication will be undertaken using the
 * given {@link AuthenticationStrategy}.
 *
 * @author Blair Butterworth
 */
public class AuthenticatedRestRequest extends RestRequest
{
    private AuthenticatedRestServer server;
    private AuthenticationStrategy strategy;

    public AuthenticatedRestRequest(
        AuthenticatedRestServer server,
        AuthenticationStrategy strategy,
        HttpRequest request,
        Serializer serializer)
    {
        super(request, serializer);
        this.server = server;
        this.strategy = strategy;
    }

    @Override
    public RestResponse make() throws RestException
    {
        try {
            strategy.authenticate(server, this);
            return super.make();
        }
        catch (RestException exception) {
            if (exception.getStatusCode() == 403) {
                strategy.reauthenticate(server, this);
                return super.make();
            }
            throw exception;
        }
    }
}