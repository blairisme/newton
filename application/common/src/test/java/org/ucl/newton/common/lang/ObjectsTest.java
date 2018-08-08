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
import java.util.Collections;

/**
 * Unit tests for the {@link Objects} class.
 *
 * @author Blair Butterworth
 */
public class ObjectsTest
{
    @Test
    public void ensureNotNullTest() {
        String expected = "foo";
        String actual = Objects.ensureNotNull("foo", "bar");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void defaultTest() {
        String expected = "foo";
        String actual = Objects.ensureNotNull(null, "foo");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void collectionTest() {
        Collection<String> expected = Arrays.asList("foo" , "bar");
        Collection<String> actual = Objects.ensureNotNull(expected);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void collectionDefaultTest() {
        Collection<String> expected = Collections.emptyList();
        Collection<String> actual = Objects.ensureNotNull(null);
        Assert.assertEquals(expected, actual);
    }
}
