/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.python;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.newton.common.lang.JarClassLoader;
import org.ucl.newton.common.lang.JarInstanceLoader;
import org.ucl.newton.sdk.processor.DataProcessor;

import java.net.URL;
import java.util.Set;

public class PythonProcessorTest
{
    @Test
    public void visualizationTest() {
        PythonProcessor processor = new PythonProcessor();
        Assert.assertTrue(! processor.getName().isEmpty());
        Assert.assertTrue(! processor.getDescription().isEmpty());
        //Assert.assertTrue(! processor.getTechnologyType().isEmpty());
    }

    @Test
    public void discoveryTest() throws Exception {
        URL jarUrl = getClass().getResource("/python.jar");
        JarClassLoader jarClassLoader = new JarClassLoader(jarUrl);
        JarInstanceLoader jarInstanceLoader = new JarInstanceLoader(jarClassLoader);
        Set<DataProcessor> processors = jarInstanceLoader.getImplementors(DataProcessor.class, "org.ucl.newton");
        Assert.assertEquals(1, processors.size());
    }
}
