package org.ucl.newton.common.identifier;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Generates human readable and URL compatible identifiers.
 *
 * @author Blair Butterworth
 * @author John Wilkie
 */
public class Identifier
{
    private static final int MAX_LENGTH = 100;

    private Identifier() {
        throw new UnsupportedOperationException();
    }

    public static String create(String name) {
        try {
            String result = name;
            result = result.replace(" ", "-");
            result = StringUtils.removePattern(result, "[.,\\/#!$%\\^&\\*;:{}=\\_`~()]");
            result = result.trim();
            result = StringUtils.substring(result, 0, MAX_LENGTH);
            return URLEncoder.encode(result, "UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            return UUID.randomUUID().toString();
        }
    }
}
