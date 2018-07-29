/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.common.lang;

import org.json.JSONWriter;
import org.junit.Assert;
import org.junit.Test;

import java.net.URL;
import java.util.Set;

public class JarInstanceLoaderTest
{
    @Test
    public void getInstanceTest() throws Exception {
        URL jarUrl = getClass().getResource("/json.jar");
        JarClassLoader jarClassLoader = new JarClassLoader(jarUrl);
        JarInstanceLoader jarInstanceLoader = new JarInstanceLoader(jarClassLoader);
        Set<JSONWriter> processors = jarInstanceLoader.getImplementors(JSONWriter.class, "org.json");
        Assert.assertEquals(1, processors.size());
    }
}
