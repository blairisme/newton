package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class ExperimentOutcomeTest
{
    @Test
    public void testEquality() {
        EqualsTester<ExperimentOutcome> equalsTester = new EqualsTester<>();
        equalsTester.forType(ExperimentOutcome.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<ExperimentOutcome> toStringTester = new ToStringTester<>();
        toStringTester.forType(ExperimentOutcome.class);
        toStringTester.test();
    }
}
