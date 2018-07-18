package org.ucl.newton.framework;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class ProjectTest
{
    @Test
    public void testEquality() {
        EqualsTester<Project> equalsTester = new EqualsTester<>();
        equalsTester.forType(Project.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<Project> toStringTester = new ToStringTester<>();
        toStringTester.forType(Project.class);
        toStringTester.test();
    }
}
