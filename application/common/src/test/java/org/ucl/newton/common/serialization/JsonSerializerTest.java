/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.serialization;

/**
 * Unit tests for the {@link JsonSerializer} class.
 *
 * @author Blair Butterworth
 */
public class JsonSerializerTest extends SerializerTest
{
    @Override
    protected Serializer getSerializer() {
        return new JsonSerializer();
    }

    @Override
    protected SerializableObject getDeserialized() {
        return new SerializableObject("foo", "bar");
    }

    @Override
    protected String getSerialized() {
        return "{\"name\":\"foo\",\"address\":\"bar\"}";
    }
}
