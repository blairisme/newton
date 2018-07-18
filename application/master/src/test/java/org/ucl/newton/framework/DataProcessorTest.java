package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class DataProcessorTest
{
    @Test
    public void testEquality() {
        EqualsTester<DataProcessor> equalsTester = new EqualsTester<>();
        equalsTester.forType(DataProcessor.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<DataProcessor> toStringTester = new ToStringTester<>();
        toStringTester.forType(DataProcessor.class);
        toStringTester.test();
    }
}
