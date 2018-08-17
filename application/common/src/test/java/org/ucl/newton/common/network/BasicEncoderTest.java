/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.network;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the {@link BasicEncoder} class.
 *
 * @author Blair Butterworth
 */
public class BasicEncoderTest
{
    @Test
    public void encodeTest() {
        String expected = "Basic YWRtaW5AdWNsLmFjLnVrOnBhc3N3b3Jk";
        String actual = BasicEncoder.encode("admin@ucl.ac.uk", "password");
        Assert.assertEquals(expected, actual);
    }
}

