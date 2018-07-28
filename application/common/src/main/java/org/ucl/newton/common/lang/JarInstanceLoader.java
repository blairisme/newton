/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.lang;

import org.apache.commons.lang3.reflect.ConstructorUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Instances of this object instance loader are used to create object instances
 * from classes in a JAR.
 *
 * @author Blair Butterworth
 */
public class JarInstanceLoader
{
    private JarClassLoader classLoader;

    public JarInstanceLoader(JarClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public <T> T getImplementor(Class<T> type, Predicate<T> predicate) throws ReflectiveOperationException {
        for (T subType: findImplementors(type)) {
            if (predicate.test(subType)){
                return subType;
            }
        }
        throw new ClassNotFoundException(type.getName());
    }

    private <T> Set<T> findImplementors(Class<T> type) throws ReflectiveOperationException {
        Set<T> result = new HashSet<>();
        for (Class<? extends T> subType: classLoader.findSubTypes(type)) {
            result.add(ConstructorUtils.invokeConstructor(subType));
        }
        return result;
    }
}
