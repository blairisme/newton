package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class ExecutorTest
{
    @Test
    public void testEquality() {
        EqualsTester<Executor> equalsTester = new EqualsTester<>();
        equalsTester.forType(Executor.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<Executor> toStringTester = new ToStringTester<>();
        toStringTester.forType(Executor.class);
        toStringTester.test();
    }
}
