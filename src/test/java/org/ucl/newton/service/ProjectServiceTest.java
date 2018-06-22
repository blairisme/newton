/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service;

import org.junit.Assert;
import org.junit.Test;
import org.ucl.newton.framework.User;

public class ProjectServiceTest
{
    @Test
    public void getProjectsTest() {
        User user = new User("test");

        ProjectService projectService = new ProjectService();
        Assert.assertNotNull(projectService.getProjects(user));
    }
}
