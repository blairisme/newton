/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.jupyter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.core.io.ClassPathResource;
import org.ucl.newton.application.system.ApplicationPreferences;
import org.ucl.newton.framework.User;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Instances of this class represent a Jupyter server. Methods are provided to
 * obtain links to login and edit.
 *
 * @author Blair Butterworth
 */
@Named
public class JupyterServer
{
    private static final String AUDIENCE = "www.newton.com";
    private static final String EXPERIMENT_PARAM = "experiment_id";
    private static final String TOKEN_PARAM = "access_token";
    private static final String USER_PREFIX = "user";
    private static final String KEY_RESOURCE = "/key/newton.key";
    private static final String LOGIN_PATH = "/login";

    private byte[] key;
    private String host;
    private int port;

    @Inject
    public JupyterServer(ApplicationPreferences applicationPreferences) {
        host = applicationPreferences.getJupyterAddress();
        port = applicationPreferences.getJupyterPort();
    }

    public URI getEditorUrl(User user, String experiment) {
        try {
            String accessToken = Jwts.builder()
                .setSubject(getUser(user))
                .setAudience(AUDIENCE)
                .signWith(SignatureAlgorithm.HS256, getKey())
                .compact();

            return new URIBuilder()
                .setScheme("http")
                .setHost(host)
                .setPort(port)
                .setPath(LOGIN_PATH)
                .addParameter(EXPERIMENT_PARAM, experiment)
                .addParameter(TOKEN_PARAM, accessToken)
                .build();
        }
        catch (URISyntaxException | IOException error){
            throw new JupyterServerException(error);
        }
    }

    private String getUser(User user) {
        return USER_PREFIX + Integer.toString(user.getId());
    }

    private byte[] getKey() throws IOException {
        if (key == null) {
            ClassPathResource resource = new ClassPathResource(KEY_RESOURCE);
            InputStream inputStream = resource.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            IOUtils.copy(inputStream, outputStream);
            key = outputStream.toByteArray();
        }
        return key;
    }
}
