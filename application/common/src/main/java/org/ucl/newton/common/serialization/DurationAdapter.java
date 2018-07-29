/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.serialization;

import com.google.gson.*;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.Duration;

/**
 * Serializes {@link Duration} objects into JSON.
 *
 * @author Blair Butterworth
 */
public class DurationAdapter implements JsonSerializer<Duration>, JsonDeserializer<Duration>
{
    @Override
    public JsonElement serialize(Duration source, Type type, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.addProperty("ISO-8601", source.toString());
        return result;
    }

    @Override
    public Duration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement element = jsonObject.get("ISO-8601");
        return Duration.parse(element.getAsString());
    }
}
