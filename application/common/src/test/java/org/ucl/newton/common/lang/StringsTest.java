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
import java.util.List;

/**
 * Unit tests for the {@link Strings} class.
 *
 * @author Blair Butterworth
 */
public class StringsTest
{
    @Test
    public void splitTest() {
        String input = "1, 2, 3";
        Collection<String> expected = Arrays.asList("1", " 2", " 3");
        Collection<String> actual = Strings.split(input, ",");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void trimTest() {
        List<String> input = Arrays.asList("1 ", " 2", " 3 ", "4");
        List<String> expected = Arrays.asList("1", "2", "3", "4");
        List<String> actual = Strings.trim(input);
        Assert.assertEquals(expected, actual);
    }
}
