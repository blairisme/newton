/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.lang;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;

/**
 * This class loader is used to load classes and resources from a search
 * path of URLs referring to both JAR files and directories.
 *
 * @author Blair Butterworth
 */
public class AmendableUrlClassLoader extends URLClassLoader
{
    public AmendableUrlClassLoader(Collection<URL> urls) {
        this(urls, AmendableUrlClassLoader.class.getClassLoader());
    }

    public AmendableUrlClassLoader(Collection<URL> urls, ClassLoader parent) {
        super(urls.toArray(new URL[0]), parent);
    }

    public void addUrl(URL url) {
        super.addURL(url);
    }

    public void addUrls(Collection<URL> urls) {
        for (URL url: urls) {
            addURL(url);
        }
    }
}