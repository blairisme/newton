package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class ExperimentConfigurationTest {

    @Test
    public void testEquality() {
        EqualsTester<ExperimentConfiguration> equalsTester = new EqualsTester<>();
        equalsTester.forType(ExperimentConfiguration.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<ExperimentConfiguration> toStringTester = new ToStringTester<>();
        toStringTester.forType(ExperimentConfiguration.class);
        toStringTester.test();
    }
}
