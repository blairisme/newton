package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class PermissionTest {

    @Test
    public void testEquality() {
        EqualsTester<Permission> equalsTester = new EqualsTester<>();
        equalsTester.forType(Permission.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<Permission> toStringTester = new ToStringTester<>();
        toStringTester.forType(Permission.class);
        toStringTester.test();
    }
}
