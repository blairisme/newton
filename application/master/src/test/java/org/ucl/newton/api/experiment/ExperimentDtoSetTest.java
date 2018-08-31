package org.ucl.newton.api.experiment;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class ExperimentDtoSetTest {

    @Test
    public void testEquality() {
        EqualsTester<ExperimentDtoSet> equalsTester = new EqualsTester<>();
        equalsTester.forType(ExperimentDtoSet.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<ExperimentDtoSet> toStringTester = new ToStringTester<>();
        toStringTester.forType(ExperimentDtoSet.class);
        toStringTester.test();
    }

}
