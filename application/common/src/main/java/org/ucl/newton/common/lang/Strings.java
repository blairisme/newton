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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Provides utility functions for working with {@link Collection Collections}
 * of {@link String Strings}.
 *
 * @author Blair Butterworth
 */
public class Strings
{
    public static List<String> split(String value, String regex) {
        return Arrays.asList(value.split(regex));
    }

    public static List<String> trim(List<String> strings) {
        List<String> result = new ArrayList<>(strings.size());
        for (String string: strings) {
            result.add(string.trim());
        }
        return result;
    }
}
