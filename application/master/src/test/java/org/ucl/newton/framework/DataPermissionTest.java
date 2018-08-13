package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class DataPermissionTest {

    @Test
    public void testEquality() {
        EqualsTester<DataPermission> equalsTester = new EqualsTester<>();
        equalsTester.forType(DataPermission.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<DataPermission> toStringTester = new ToStringTester<>();
        toStringTester.forType(DataPermission.class);
        toStringTester.test();
    }
}
