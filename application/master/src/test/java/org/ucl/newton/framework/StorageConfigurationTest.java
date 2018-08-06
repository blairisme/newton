package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class StorageConfigurationTest
{
    @Test
    public void testEquality() {
        EqualsTester<StorageConfiguration> equalsTester = new EqualsTester<>();
        equalsTester.forType(StorageConfiguration.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<StorageConfiguration> toStringTester = new ToStringTester<>();
        toStringTester.forType(StorageConfiguration.class);
        toStringTester.test();
    }
}
