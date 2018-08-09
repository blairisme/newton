/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Unit tests for the {@link CollectionUtils} class.
 *
 * @author Blair Butterworth
 */
public class CollectionUtilsTest
{
    @Test
    public void appendTest() {
        List<String> original = new ArrayList<>();
        original.add("foo");

        List<String> expected = new ArrayList<>();
        expected.add("foo");
        expected.add("bar");

        Collection<String> actual = CollectionUtils.append(original, "bar");

        Assert.assertEquals(expected, actual);
        Assert.assertEquals(1, original.size());
        Assert.assertTrue(original.contains("foo"));
    }
}
