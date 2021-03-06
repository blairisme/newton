/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */


package org.ucl.newton.common.network;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.ucl.newton.common.serialization.Serializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Instances of this class represent a request to a REST service. Methods exist
 * to set the query parameters and body of the request as well as to make the
 * request itself.
 *
 * @author Blair Butterworth
 */
public class RestRequest
{
    protected Serializer serializer;
    protected HttpRequest request;
    protected RestStatusStrategy statusHandler;

    RestRequest(HttpRequest request, Serializer serializer)
    {
        this.request = request;
        this.serializer = serializer;
        this.statusHandler = RestStatusStrategies.throwOnFailure();
    }

    public RestRequest setHeader(Object key, Object value) {
        request.header(convert(key), convert(value));
        return this;
    }

    public RestRequest setParameters(Map<Object, Object> parameters) {
        request.queryString(convert(parameters));
        return this;
    }

    public RestRequest setBody(String value)
    {
        if (request instanceof HttpRequestWithBody) {
            HttpRequestWithBody requestWithBody = (HttpRequestWithBody)request;
            requestWithBody.body(value);
            return this;
        }
        throw new IllegalArgumentException();
    }

    public <T> RestRequest setBody(T value, Class<T> type)
    {
        return setBody(serializer.serialize(value, type));
    }

    public void setStatusStrategy(RestStatusStrategy statusStrategy) {
        this.statusHandler = statusStrategy;
    }

    public RestResponse make() throws RestException {
        try {
            HttpResponse<String> response = request.asString();
            RestResponse result = new RestResponse(response, serializer);

            if (! statusHandler.test(result)) {
                throw new RestException(result.getStatusCode());
            }
            return result;
        }
        catch (UnirestException exception){
            throw new RestException(exception);
        }
    }

    private Map<String, Object> convert(Map<Object, Object> values) {
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<Object, Object> entry: values.entrySet()){
            result.put(convert(entry.getKey()), convert(entry.getValue()));
        }
        return result;
    }

    protected String convert(Object object) {
        return object != null ? object.toString() : null;
    }
}
