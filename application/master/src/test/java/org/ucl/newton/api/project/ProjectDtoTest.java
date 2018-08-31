package org.ucl.newton.api.project;

import org.junit.Test;
import org.ucl.newton.test.EqualsTester;
import org.ucl.newton.test.ToStringTester;

public class ProjectDtoTest {

    @Test
    public void testEquality() {
        EqualsTester<ProjectDto> equalsTester = new EqualsTester<>();
        equalsTester.forType(ProjectDto.class);
        equalsTester.test();
    }

    @Test
    public void testToString() {
        ToStringTester<ProjectDto> toStringTester = new ToStringTester<>();
        toStringTester.forType(ProjectDto.class);
        toStringTester.test();
    }
}
