/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.lang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Instances of this class provide utility functions for use working with
 * Objects.
 *
 * @author Blair Butterworth
 */
public class Objects
{
    private Objects() {
        throw new UnsupportedOperationException();
    }

    public static <T> Collection<T> ensureNotNull(Collection<T> values) {
        return ensureNotNull(values, new ArrayList<T>());
    }

    public static Map ensureNotNull(Map values) {
        return ensureNotNull(values, new HashMap<>());
    }

    public static <T> T ensureNotNull(T value, T alternative) {
        return value != null ? value : alternative;
    }
}
