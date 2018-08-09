/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.network;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.Validate;

/**
 * Utility class for encoding credentials info the format expected when using
 * HTTP basic authentication.
 *
 * @author Blair Butterworth
 */
public class BasicEncoder
{
    private BasicEncoder() {
        throw new UnsupportedOperationException();
    }

    public static String encode(String username, String password) {
        Validate.notNull(username);
        Validate.notNull(password);

        String plainCredentials = username + ":" + password;
        String encodedCredentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
        return "Basic " + encodedCredentials;
    }
}
