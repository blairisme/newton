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
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.ucl.newton.common.exception.ConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * A general purpose HTTP connection with support for basic authentication.
 *
 * @author Blair Butterworth
 */
public class BasicHttpConnection
{
    private URI address;
    private Map<String, String> headers;

    public BasicHttpConnection() {
    }

    public BasicHttpConnection(URI address) {
        this.address = address;
        this.headers = new HashMap<>();
    }

    public void setBasicAuthorization(String username, String password) {
        setHeader(HttpHeaders.AUTHORIZATION, BasicEncoder.encode(username, password));
    }

    public void setAddress(URI address) {
        this.address = address;
    }

    public void setHeader(String key, String value){
        this.headers.put(key, value);
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setParameters(Map<String, String> parameters) throws URISyntaxException {
        Validate.notNull(address);
        Validate.notNull(parameters);

        URIBuilder builder = new URIBuilder(address);
        parameters.forEach((key, value) -> builder.addParameter(key, value));

        address = builder.build();
    }

    public InputStream getInputStream() throws IOException {
        return getInputStream(statusLine -> true);
    }

    public InputStream getInputStream(Predicate<StatusLine> statusStrategy) throws IOException {
        Validate.notNull(address);
        Validate.notNull(headers);

        HttpClientBuilder builder = HttpClientBuilder.create();
        HttpClient client = builder.build();

        HttpGet request = new HttpGet(address);
        headers.forEach(request::addHeader);

        HttpResponse response = client.execute(request);
        StatusLine status = response.getStatusLine();

        if (statusStrategy.test(status)) {
            HttpEntity entity = response.getEntity();
            return entity.getContent();
        }
        throw new ConnectionException(status.getReasonPhrase());
    }

    public static Predicate<StatusLine> statusBetween(int fromCode, int toCode) {
        return predicate -> predicate.getStatusCode() >= fromCode && predicate.getStatusCode() < toCode;
    }
}
