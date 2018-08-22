/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.plugin;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class PluginTest
{
    @Test
    public void testEquality() {
        EqualsTester<Plugin> equalsTester = new EqualsTester<>();
        equalsTester.forType(Plugin.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<Plugin> toStringTester = new ToStringTester<>();
        toStringTester.forType(Plugin.class);
        toStringTester.test();
    }
}
