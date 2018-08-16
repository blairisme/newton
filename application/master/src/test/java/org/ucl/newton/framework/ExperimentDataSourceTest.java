package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class ExperimentDataSourceTest {
    @Test
    public void testEquality() {
        EqualsTester<ExperimentDataSource> equalsTester = new EqualsTester<>();
        equalsTester.forType(ExperimentDataSource.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<ExperimentDataSource> toStringTester = new ToStringTester<>();
        toStringTester.forType(ExperimentDataSource.class);
        toStringTester.test();
    }
}
