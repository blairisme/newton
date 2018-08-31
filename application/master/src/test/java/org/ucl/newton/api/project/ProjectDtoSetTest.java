package org.ucl.newton.api.project;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class ProjectDtoSetTest {

    @Test
    public void testEquality() {
        EqualsTester<ProjectDtoSet> equalsTester = new EqualsTester<>();
        equalsTester.forType(ProjectDtoSet.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<ProjectDtoSet> toStringTester = new ToStringTester<>();
        toStringTester.forType(ProjectDtoSet.class);
        toStringTester.test();
    }
}
