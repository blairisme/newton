/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.lang;

import org.reflections.Reflections;

import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Instances of this class loader are used to load classes and resources from a
 * JAR.
 *
 * @author Blair Butterworth
 */
public class JarClassLoader extends AmendableUrlClassLoader
{
    private static JarClassLoader instance;

    public static JarClassLoader getSystemClassLoader() {
        if (instance == null) {
            instance = new JarClassLoader();
        }
        return instance;
    }

    public JarClassLoader() {
        super(Collections.emptyList());
    }

    public JarClassLoader(URL jar) {
        super(Arrays.asList(jar));
    }

    public void load(URL jar) {
        addUrl(jar);
    }

    public void load(Collection<URL> jars) {
        addUrls(jars);
    }

    public <T> Set<Class<? extends T>> findSubTypes(Class<T> type) {
        Reflections reflections = new Reflections(this);
        return reflections.getSubTypesOf(type);
    }

    public <T> Set<Class<? extends T>> findSubTypes(Class<T> type, String packageRestriction) {
        Reflections reflections = new Reflections(packageRestriction, this);
        return reflections.getSubTypesOf(type);
    }
}