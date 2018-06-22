/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui.project;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;
import org.ucl.newton.service.ProjectService;
import org.ucl.newton.service.UserService;

public class ProjectControllerTest
{
    @Test
    public void listTest(){
        UserService userService = Mockito.mock(UserService.class);
        ProjectService projectService = Mockito.mock(ProjectService.class);

        ProjectController controller = new ProjectController(userService, projectService);
        Assert.assertEquals("ProjectList", controller.list(new ModelMap()));
    }

    @Test
    public void detailsTest(){
        UserService userService = Mockito.mock(UserService.class);
        ProjectService projectService = Mockito.mock(ProjectService.class);

        ProjectController controller = new ProjectController(userService, projectService);
        Assert.assertEquals("ProjectDetails", controller.details("test", new ModelMap()));
    }
}
