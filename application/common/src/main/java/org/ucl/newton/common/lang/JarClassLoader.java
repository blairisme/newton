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

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import static org.ucl.newton.common.file.PathUtils.toUrl;

/**
 * Instances of this class loader are used to load classes and resources from a
 * JAR.
 *
 * @author Blair Butterworth
 */
public class JarClassLoader implements Closeable
{
    private URLClassLoader delegate;

    public JarClassLoader(Path jar) {
        this(toUrl(jar));
    }

    public JarClassLoader(URL jar) {
        this(Arrays.asList(jar));
    }

    public JarClassLoader(Collection<URL> jars) {
        Validate.notNull(jars);
        ClassLoader parent = getClass().getClassLoader();
        delegate = new URLClassLoader(jars.toArray(new URL[0]), parent);
    }

    public <T> Set<Class<? extends T>> findSubTypes(Class<T> type) {
        Reflections reflections = new Reflections(delegate);
        return reflections.getSubTypesOf(type);
    }

    public <T> Set<Class<? extends T>> findSubTypes(Class<T> type, String packageRestriction) {
        Reflections reflections = new Reflections(packageRestriction, delegate);
        return reflections.getSubTypesOf(type);
    }

    @Override
    public void close() throws IOException {
        delegate.close();
    }
}