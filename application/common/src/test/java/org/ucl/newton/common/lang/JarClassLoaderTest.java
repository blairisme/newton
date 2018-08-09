/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.lang;

import org.json.JSONTokener;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.Set;

/**
 * Unit tests for the {@link JarClassLoader} class.
 *
 * @author Blair Butterworth
 */
public class JarClassLoaderTest
{
    @Test
    public void findSubTypesTest() {
        URL jarUrl = getClass().getResource("/json.jar");
        JarClassLoader jarClassLoader = new JarClassLoader(jarUrl);
        Set<Class<? extends JSONTokener>> result = jarClassLoader.findSubTypes(JSONTokener.class, "org.json");
        Assert.assertEquals(2, result.size());
    }
}
