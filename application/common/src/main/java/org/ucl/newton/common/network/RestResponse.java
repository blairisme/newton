/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.network;

import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import org.ucl.newton.common.serialization.Serializer;

import java.util.Arrays;
import java.util.Collection;

/**
 * Instances of this class represent a response from a REST service call.
 * Methods exist to obtain whether the request was successful and where
 * applicable object returned by the REST service.
 *
 * @author Blair Butterworth
 */
public class RestResponse
{
    private Serializer serializer;
    private HttpResponse<String> response;

    RestResponse(HttpResponse<String> response, Serializer serializer) {
        this.response = response;
        this.serializer = serializer;
    }

    public int getStatusCode() {
        return response.getStatus();
    }

    public String getHeader(String key) {
        Headers headers = response.getHeaders();
        return headers.getFirst(key);
    }

    public boolean hasStatusCode(Integer ... codes) {
        return hasStatusCode(Arrays.asList(codes));
    }

    public boolean hasStatusCode(Collection<Integer> codes) {
        return codes.contains(getStatusCode());
    }

    public boolean isEmpty() {
        String body = response.getBody();
        return body == null || body.isEmpty();
    }

    public String asString() {
        return response.getBody();
    }

    public <T> T asType(Class<T> type) {
        return serializer.deserialize(response.getBody(), type);
    }
}
