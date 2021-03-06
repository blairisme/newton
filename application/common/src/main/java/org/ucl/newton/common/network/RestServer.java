/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */


package org.ucl.newton.common.network;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.ucl.newton.common.serialization.JsonSerializer;
import org.ucl.newton.common.serialization.Serializer;

import javax.inject.Inject;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Instances of this class represent a representational state transfer (REST)
 * web service.
 *
 * @author Blair Butterworth
 */
public class RestServer
{
    private String address;
    private Serializer serializer;
    private Map<String, String> headers;

    @Inject
    public RestServer() {
        this.address = "http://localhost:9090/api";
        this.serializer = new JsonSerializer();
        this.headers = new HashMap<>();
    }

    public RestServer(RestServer another) {
        this.address = another.address;
        this.serializer = another.serializer;
        this.headers = another.headers;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddress(URI address) {
        this.address = address.toString();
    }

    public void setSerializer(Serializer serializer) {
        this.serializer = serializer;
    }

    public void addHeader(Object key, Object value) {
        this.headers.put(convert(key), convert(value));
    }

    public void setHeader(Object key, Object value) {
        this.headers.put(convert(key), convert(value));
    }

    public RestRequest get(RestResource resource) {
        return get(resource.getPath());
    }

    public RestRequest get(String path) {
        String url = combine(address, path);
        GetRequest request = Unirest.get(url).headers(headers);
        return newRestRequest(request, serializer);
    }

    public RestRequest post(RestResource resource) {
        return post(resource.getPath());
    }

    public RestRequest post(String path) {
        String url = combine(address, path);
        HttpRequestWithBody request = Unirest.post(url).headers(headers);
        return newRestRequest(request, serializer);
    }

    public RestRequest put(RestResource resource) {
        return put(resource.getPath());
    }

    public RestRequest put(String path) {
        String url = combine(address, path);
        HttpRequestWithBody request = Unirest.put(url).headers(headers);
        return newRestRequest(request, serializer);
    }

    public RestRequest delete(RestResource resource) {
        return delete(resource.getPath());
    }

    public RestRequest delete(String path) {
        String url = combine(address, path);
        HttpRequest request = Unirest.delete(url).headers(headers);
        return newRestRequest(request, serializer);
    }

    protected String convert(Object object) {
        return object != null ? object.toString() : null;
    }

    protected RestRequest newRestRequest(HttpRequest request, Serializer serializer) {
        return new RestRequest(request, serializer);
    }

    private String combine(String root, String path) {

        StringBuilder result = new StringBuilder();
        result.append(root);

        if (!root.endsWith("/") && !path.startsWith("/")){
            result.append("/");
        }
        result.append(path);
        return result.toString();
    }
}
