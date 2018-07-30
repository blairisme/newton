/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.bridge;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.newton.common.serialization.JsonSerializer;
import org.ucl.newton.common.serialization.Serializer;

import java.net.URL;
import java.time.Duration;
import java.util.Date;

public class ExecutionResultTest
{
    @Test
    public void serializeTest() throws Exception {
        Serializer serializer = new JsonSerializer();

        ExecutionResult expected = new ExecutionResult("1", "experiment-1", "2", new Date(), Duration.ZERO, new URL("http://wwwnewton.com"));
        String serialized = serializer.serialize(expected, ExecutionResult.class);
        ExecutionResult actual = serializer.deserialize(serialized, ExecutionResult.class);

        Assert.assertEquals(expected, actual);
    }
}
