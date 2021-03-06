package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class CredentialTest
{
    @Test
    public void testEquality() {
        EqualsTester<Credential> equalsTester = new EqualsTester<>();
        equalsTester.forType(Credential.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<Credential> toStringTester = new ToStringTester<>();
        toStringTester.forType(Credential.class);
        toStringTester.test();
    }
}
