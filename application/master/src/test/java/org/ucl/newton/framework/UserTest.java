package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class UserTest
{
    @Test
    public void testEquality() {
        EqualsTester<User> equalsTester = new EqualsTester<>();
        equalsTester.setEqualObjects(createUserA(), createUserA(), createUserA());
        equalsTester.setDifferentObject(createUserB());
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<User> toStringTester = new ToStringTester<>();
        toStringTester.setObject(createUserA());
        toStringTester.test();
    }

    private User createUserA() {
        return new User(0, "user", "user@email.com", UserRole.USER, "image.png");
    }

    private User createUserB() {
        return new User(0, "foo", "user@email.com", UserRole.USER, "image.png");
    }
}