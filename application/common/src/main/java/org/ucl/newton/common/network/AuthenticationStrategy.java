/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.network;

/**
 * Implementors of this interface provide an authentication strategy for a
 * given {@link RestServer}.
 *
 * @author Blair Butterworth
 */
public interface AuthenticationStrategy
{
    void authenticate(RestServer server, RestRequest request) throws RestException;

    void reauthenticate(RestServer server, RestRequest request) throws RestException;
}