/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.Writer;
import java.time.Duration;

/**
 * Instances of this class serialize objects into their equivalent JSON
 * representation. Methods are provided to convert Java objects into JSON
 * and JSON into Java objects.
 *
 * @author Blair Butterworth
 */
public class JsonSerializer implements Serializer
{
    private Gson gson;

    public JsonSerializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationAdapter());
        gson = gsonBuilder.create();
    }

    @Override
    public <T> String serialize(T value, Class<T> type) throws SerializationException {
        try {
            return gson.toJson(value, type);
        }
        catch (Throwable error) {
            throw new SerializationException(error);
        }
    }

    @Override
    public <T> void serialize(T value, Class<T> type, Writer writer) throws SerializationException {
        try {
            gson.toJson(value, type, writer);
        }
        catch (Throwable error) {
            throw new SerializationException(error);
        }
    }

    @Override
    public <T> T deserialize(String value, Class<T> type) throws SerializationException {
        try {
            return gson.fromJson(value, type);
        }
        catch (Throwable error) {
            throw new SerializationException(error);
        }
    }

    @Override
    public <T> T deserialize(Reader reader, Class<T> type) throws SerializationException {
        try {
            return gson.fromJson(reader, type);
        }
        catch (Throwable error) {
            throw new SerializationException(error);
        }
    }
}
