/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.lang;

import org.apache.commons.lang3.Validate;
import org.reflections.Reflections;
import org.ucl.newton.common.file.IoUtils;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

/**
 * Instances of this class loader are used to load classes and resources from a
 * JAR.
 *
 * @author Blair Butterworth
 */
public class JarClassLoader extends DelegateClassLoader implements Closeable
{
    private static JarClassLoader instance;

    public static JarClassLoader getSystemClassLoader() {
        if (instance == null) {
            instance = new JarClassLoader();
        }
        return instance;
    }

    private Collection<URL> jars;
    private ClassLoader parent;

    public JarClassLoader() {
        this(Collections.emptyList());
    }

    public JarClassLoader(URL jar) {
        this(Arrays.asList(jar));
    }

    public JarClassLoader(Collection<URL> jars) {
        Validate.notNull(jars);
        this.jars = new ArrayList<>();
        this.jars.addAll(jars);
        this.parent = getClass().getClassLoader();
        updateDelegate();
    }

    public void load(URL jar) {
        this.jars.add(jar);
        updateDelegate();
    }

    public void load(Collection<URL> jars) {
        this.jars.clear();
        this.jars.addAll(jars);
        updateDelegate();
    }

    public <T> Set<Class<? extends T>> findSubTypes(Class<T> type) {
        Reflections reflections = new Reflections(getDelegate());
        return reflections.getSubTypesOf(type);
    }

    public <T> Set<Class<? extends T>> findSubTypes(Class<T> type, String packageRestriction) {
        Reflections reflections = new Reflections(packageRestriction, getDelegate());
        return reflections.getSubTypesOf(type);
    }

    @Override
    public void close() throws IOException {
        URLClassLoader classLoader = (URLClassLoader)getDelegate();
        classLoader.close();
    }

    private void updateDelegate() {
        IoUtils.closeQuitely((URLClassLoader)getDelegate());
        setDelegate(new URLClassLoader(jars.toArray(new URL[0]), parent));
    }
}