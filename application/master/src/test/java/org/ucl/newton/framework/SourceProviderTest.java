package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class SourceProviderTest
{
    @Test
    public void testEquality() {
        EqualsTester<SourceProvider> equalsTester = new EqualsTester<>();
        equalsTester.forType(SourceProvider.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<SourceProvider> toStringTester = new ToStringTester<>();
        toStringTester.forType(SourceProvider.class);
        toStringTester.test();
    }
}
