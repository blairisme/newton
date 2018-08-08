/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.serialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.lang.reflect.Type;
import java.time.Duration;

/**
 * Unit tests for the {@link DurationAdapter} class.
 *
 * @author Blair Butterworth
 */
public class DurationAdapterTest
{
    @Test
    public void serializeTest() throws Exception {
        Duration duration = Duration.ofMillis(123456);

        JsonObject expected = new JsonObject();
        expected.addProperty("ISO-8601", duration.toString());

        Type type = Mockito.mock(Type.class);
        JsonSerializationContext context = Mockito.mock(JsonSerializationContext.class);

        DurationAdapter adapter = new DurationAdapter();
        JsonElement actual = adapter.serialize(duration, type, context);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deserialize() {
        Duration expected = Duration.ofMillis(123456);

        JsonObject json = new JsonObject();
        json.addProperty("ISO-8601", expected.toString());

        Type type = Mockito.mock(Type.class);
        JsonDeserializationContext context = Mockito.mock(JsonDeserializationContext.class);

        DurationAdapter adapter = new DurationAdapter();
        Duration actual = adapter.deserialize(json, type, context);

        Assert.assertEquals(expected, actual);
    }
}
