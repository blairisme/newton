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
import org.ucl.newton.framework.User;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
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
    private static final String NEWTON_URL = "www.newton.com";
    private static final String JUPYTER_URL = "http://localhost:8000/login";
    private static final String EXPERIMENT_PARAM = "experiment_id";
    private static final String TOKEN_PARAM = "access_token";
    private static final String USER_PREFIX = "user";
    private static final String KEY_PATH = "/key/newton.key";

    private byte[] key;

    @Inject
    public JupyterServer() {
    }

    public URI getEditorUrl(User user, String experiment) {
        try {
            String accessToken = Jwts.builder()
                    .setSubject(getUser(user))
                    .setAudience(NEWTON_URL)
                    .signWith(SignatureAlgorithm.HS256, getKey())
                    .compact();

            return new URIBuilder(JUPYTER_URL)
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
            ClassPathResource resource = new ClassPathResource(KEY_PATH);
            InputStream inputStream = resource.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            IOUtils.copy(inputStream, outputStream);
            key = outputStream.toByteArray();
        }
        return key;
    }
}
