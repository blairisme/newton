/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.lang;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

/**
 * Unit tests for the {@link Integers} class.
 *
 * @author Blair Butterworth
 */
public class IntegersTest
{
    @Test
    public void stringToIntTest() {
        Collection<String> input = Arrays.asList("1", "2", "3");
        Collection<Integer> expected = Arrays.asList(1, 2, 3);
        Collection<Integer> actual = Integers.stringToInt(input);
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = NumberFormatException.class)
    public void invalidTest() {
        Collection<String> input = Arrays.asList("1", "a", "3");
        Integers.stringToInt(input);
    }
}
