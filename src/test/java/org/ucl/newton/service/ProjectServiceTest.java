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
import org.mockito.Mockito;
import org.ucl.newton.framework.User;
import org.ucl.newton.service.project.ProjectRepository;
import org.ucl.newton.service.project.ProjectService;

public class ProjectServiceTest
{
    @Test
    public void getProjectsTest() {
        User user = Mockito.mock(User.class);
        ProjectRepository repository = Mockito.mock(ProjectRepository.class);

        ProjectService projectService = new ProjectService(repository);
        Assert.assertNotNull(projectService.getProjects(user));
    }
}
