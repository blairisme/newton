package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class CredentialTest
{
    @Test
    public void testEquality() {
        EqualsTester<Credential> equalsTester = new EqualsTester<>();
        equalsTester.setEqualObjects(createCredentialA(), createCredentialA(), createCredentialA());
        equalsTester.setDifferentObject(createCredentialB());
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<Credential> toStringTester = new ToStringTester<>();
        toStringTester.setObject(createCredentialA());
        toStringTester.test();
    }

    private Credential createCredentialA() {
        return new Credential(0, 0, "user", "password");
    }

    private Credential createCredentialB() {
        return new Credential(0, 0, "foo", "bar");
    }
}
