/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.network;

import org.apache.http.client.utils.URIBuilder;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

/**
 * Utility functions for work with {@link URL URLS}.
 *
 * @author Blair Butterworth
 */
public class UrlUtils
{
    private UrlUtils() {
        throw new UnsupportedOperationException();
    }

    public static URL createUrl(URL base, Path path) throws MalformedURLException {
        try {
            URIBuilder builder = new URIBuilder(base.toURI());
            builder.setPath(builder.getPath() + getUrlPath(path));
            return builder.build().toURL();
        }
        catch (URISyntaxException syntaxException) {
            throw new MalformedURLException(syntaxException.getMessage());
        }
    }

    private static String getUrlPath(Path filePath) {
        String result = filePath.toString();
        result = result.replace("\\", "/");
        result = result.startsWith("/") ? result :  "/" + result;
        return result;
    }
}
