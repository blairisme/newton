package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class DataSourceTest 
{
    @Test
    public void testEquality() {
        EqualsTester<DataSource> equalsTester = new EqualsTester<>();
        equalsTester.forType(DataSource.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<DataSource> toStringTester = new ToStringTester<>();
        toStringTester.forType(DataSource.class);
        toStringTester.test();
    }
}
