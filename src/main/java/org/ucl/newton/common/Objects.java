/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Instances of this class provide utility functions for use working with
 * Objects.
 *
 * @author Blair Butterworth
 */
public class Objects
{
    public static <T> Collection<T> ensureNotNull(Collection<T> values) {
        return ensureNotNull(values, new ArrayList<T>());
    }

    public static <T> T ensureNotNull(T value, T alternative) {
        return value != null ? value : alternative;
    }
}
