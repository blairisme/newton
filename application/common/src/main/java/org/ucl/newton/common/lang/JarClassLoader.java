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

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
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
        URL[] urls = {toUrl(jar)};
        ClassLoader parent = getClass().getClassLoader();
        delegate = new URLClassLoader(urls, parent);
    }

    public <T> Set<Class<? extends T>> findSubTypes(Class<T> type) {
        Reflections reflections = new Reflections(delegate);
        return reflections.getSubTypesOf(type);
    }

    @Override
    public void close() throws IOException {
        delegate.close();
    }
}