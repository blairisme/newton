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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.framework.Project;
import org.ucl.newton.framework.User;
import org.ucl.newton.service.project.ProjectRepository;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@ActiveProfiles("development")
public class ProjectRepositoryTest
{
    @Inject
    private ProjectRepository repository;
    private User owner;

    @Before
    public void set_up(){
        owner = new User("admin", "$2a$10$jECDv6NZWiMz2k9i9Fw50u5TW3Q4xZ8/gXCc86Q6lZ5.k9A2YrF7m",
                "Admin User", "ADMINISTRATOR");
    }

    @Test
    public void getProjectTest() throws Exception {
        Project expected = new Project("project-fizzyo", "project Fizzyo", "project Fizzyo Description",
                getDate("2018-06-20 12:34:56"), owner);
        Project actual = repository.getProject("project-fizzyo");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getProjectsTest() {
        List<Project> projects = repository.getProjects(0, 20);
        Assert.assertEquals(3, projects.size());
    }

    @Test
    public void addProjectTest() throws Exception {
        Project expected = new Project("project-a", "project A", "project A Description",
                getDate("2018-06-20 12:34:56"), owner);
        repository.addProject(expected);
        Project actual = repository.getProject("project-a");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void removeProjectTest() throws Exception {
        Project project = new Project("project-b", "project B", "project B Description",
                getDate("2018-06-20 12:34:56"), owner);

        repository.addProject(project);
        Project before = repository.getProject("project-b");
        Assert.assertNotNull(before);

        repository.removeProject(before);
        Project after = repository.getProject("project-b");
        Assert.assertNull(after);
    }

    @Test
    public void testProjectOwner() throws Exception {
        Project expected = new Project("project-c", "project c", "Project C Description", getDate("2018-06-20 12:34:56"), owner);
        repository.addProject(expected);
        Project actual = repository.getProject("project-c");
        Assert.assertEquals(expected.getOwner(), actual.getOwner());
    }

    private Date getDate(String date) throws ParseException  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(date);
    }
}
