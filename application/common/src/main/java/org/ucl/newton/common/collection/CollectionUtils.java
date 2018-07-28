/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Utility functions for use working with {@link Collection Collections}.
 *
 * @author Blair Butterworth
 */
public class CollectionUtils
{
    public static <T> List<T> append(List<T> list, T value) {
        List<T> result = new ArrayList<>(list);
        result.add(value);
        return result;
    }
}
