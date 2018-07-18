package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class UserTest
{
    @Test
    public void testEquality() {
        EqualsTester<User> equalsTester = new EqualsTester<>();
        equalsTester.forType(User.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<User> toStringTester = new ToStringTester<>();
        toStringTester.forType(User.class);
        toStringTester.test();
    }
}