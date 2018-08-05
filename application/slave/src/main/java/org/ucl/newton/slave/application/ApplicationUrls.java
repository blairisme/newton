/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.slave.application;

import org.apache.http.client.utils.URIBuilder;
import org.ucl.newton.common.network.UriSchemes;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Defines URLS exposed by the slave application.
 *
 * @author Blair Butterworth
 * @author Ziad Halabi
 */
@Named
public class ApplicationUrls
{
    private ApplicationPreferences preferences;

    @Inject
    public ApplicationUrls(ApplicationPreferences preferences) {
        this.preferences = preferences;
    }

    public URL getFilesUrl() {
        try {
            URIBuilder builder = new URIBuilder();
            builder.setScheme(UriSchemes.HTTP);
            builder.setHost(preferences.getApplicationHost());
            builder.setPort(preferences.getApplicationPort());
            builder.setPath("files");
            return builder.build().toURL();
        }
        catch (URISyntaxException | MalformedURLException error) {
            throw new IllegalStateException(error.getMessage());
        }
    }
}
