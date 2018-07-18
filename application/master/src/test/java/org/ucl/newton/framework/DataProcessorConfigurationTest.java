package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class DataProcessorConfigurationTest
{
    @Test
    public void testEquality() {
        EqualsTester<DataProcessorConfiguration> equalsTester = new EqualsTester<>();
        equalsTester.forType(DataProcessorConfiguration.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<DataProcessorConfiguration> toStringTester = new ToStringTester<>();
        toStringTester.forType(DataProcessorConfiguration.class);
        toStringTester.test();
    }
}
