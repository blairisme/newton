/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.test;

import org.apache.commons.lang3.Validate;
import org.mockito.Mockito;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Instances of this class build objects using mock data.
 *
 * @author Blair Butterworth
 */
public class ObjectBuilder
{
    @SuppressWarnings("unchecked")
    public static <T> T newInstance(Class<T> type) throws ObjectBuilderException {
        try {
            Constructor<?> constructor = getConstructor(type);
            Collection<Object> parameters = getParameters(constructor);

            constructor.setAccessible(true);
            return (T) constructor.newInstance(parameters.toArray());
        }
        catch (Exception error) {
            throw new ObjectBuilderException(error);
        }
    }

    private static <T> Constructor<?> getConstructor(Class<T> type) {
        Constructor<?> result = null;
        Constructor<?>[] constructors = type.getDeclaredConstructors();

        for (Constructor<?> constructor: constructors) {
            if (result == null || constructor.getParameterCount() > result.getParameterCount()) {
                result = constructor;
            }
        }
        Validate.notNull(result, "Missing constructor...");
        return result;
    }

    private static Collection<Object> getParameters(Constructor<?> constructor) {
        Collection<Object> result = new ArrayList<>();
        for (Parameter parameter:  constructor.getParameters()){
            result.add(getValue(parameter.getType()));
        }
        return result;
    }

    private static Object getValue(Class<?> type) {
        Random random = new Random();

        if (type.isAssignableFrom(String.class)) {
            return Integer.toString(random.nextInt());
        }
        if (type.isAssignableFrom(Duration.class)) {
            return Duration.ofMillis(random.nextLong());
        }
        if (type.isAssignableFrom(int.class) || type.isAssignableFrom(Integer.class)) {
            return random.nextInt();
        }
        if (type.isAssignableFrom(long.class) || type.isAssignableFrom(Long.class)) {
            return random.nextLong();
        }
        if (type.isAssignableFrom(double.class) || type.isAssignableFrom(Double.class)) {
            return random.nextDouble();
        }
        if (type.isAssignableFrom(float.class) || type.isAssignableFrom(Float.class)) {
            return random.nextFloat();
        }
        if (type.isAssignableFrom(boolean.class) || type.isAssignableFrom(Boolean.class)) {
            return random.nextBoolean();
        }
        if (type.isEnum()) {
            Object[] enumConstants = type.getEnumConstants();
            return enumConstants[random.nextInt(enumConstants.length)];
        }
        return Mockito.mock(type);
    }
}
