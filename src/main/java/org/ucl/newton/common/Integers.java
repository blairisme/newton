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

/**
 * Instances of this class provide utility functions for use working with
 * Integers.
 *
 * @author Blair Butterworth
 */
public class Integers
{
    public static Collection<Integer> parse(Collection<String> values){
        Collection<Integer> result = new ArrayList<>();
        for (String value: values) {
            result.add(Integer.parseInt(value));
        }
        return result;
    }
}
