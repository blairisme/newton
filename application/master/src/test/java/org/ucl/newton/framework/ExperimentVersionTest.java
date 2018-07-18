package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class ExperimentVersionTest
{
    @Test
    public void testEquality() {
        EqualsTester<ExperimentVersion> equalsTester = new EqualsTester<>();
        equalsTester.forType(ExperimentVersion.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<ExperimentVersion> toStringTester = new ToStringTester<>();
        toStringTester.forType(ExperimentVersion.class);
        toStringTester.test();
    }
}
