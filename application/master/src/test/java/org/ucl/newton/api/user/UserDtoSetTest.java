package org.ucl.newton.api.user;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class UserDtoSetTest {

    @Test
    public void testEquality() {
        EqualsTester<UserDtoSet> equalsTester = new EqualsTester<>();
        equalsTester.forType(UserDtoSet.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<UserDtoSet> toStringTester = new ToStringTester<>();
        toStringTester.forType(UserDtoSet.class);
        toStringTester.test();
    }
}
