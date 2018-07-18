package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class ExperimentTest
{
    @Test
    public void testEquality() {
        EqualsTester<Experiment> equalsTester = new EqualsTester<>();
        equalsTester.forType(Experiment.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<Experiment> toStringTester = new ToStringTester<>();
        toStringTester.forType(Experiment.class);
        toStringTester.test();
    }
}
