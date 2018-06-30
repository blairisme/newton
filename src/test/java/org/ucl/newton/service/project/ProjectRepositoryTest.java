/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.project;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.framework.Project;
import org.ucl.newton.framework.ProjectBuilder;
import org.ucl.newton.framework.User;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@ActiveProfiles("development")
public class ProjectRepositoryTest
{
    @Inject
    private ProjectRepository repository;


    @Test
    public void addProjectTest() throws Exception {
        Project expected = createProject("project-a", "project A");
        repository.addProject(expected);

        Project actual = repository.getProjectByIdentifier("project-a");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getProjectsTest() {
        List<Project> projects = repository.getProjects(0, 20);
        Assert.assertEquals(3, projects.size());
    }

    @Test
    public void removeProjectTest() throws Exception {
        Project project = createProject("project-b", "project B");

        repository.addProject(project);
        Project before = repository.getProjectByIdentifier("project-b");
        Assert.assertNotNull(before);

        repository.removeProject(before);
        Project after = repository.getProjectById(project.getId());
        Assert.assertNull(after);
    }

    @Test
    public void testProjectOwner() throws Exception {
        Project expected = createProject("project-c", "project c");
        repository.addProject(expected);
        Project actual = repository.getProjectByIdentifier("project-c");
        Assert.assertEquals(expected.getOwner(), actual.getOwner());
    }

    @Test
    public void membersTest() {
        Project project = repository.getProjectByIdentifier("project-fizzyo");
        Collection<User> members = project.getMembers();
        Assert.assertEquals(2, members.size());
    }

    private Project createProject(String identifier, String name) throws Exception {
        ProjectBuilder projectBuilder = new ProjectBuilder();
        projectBuilder.setIdentifier(identifier);
        projectBuilder.setName(name);
        projectBuilder.setDescription("project A Description");
        projectBuilder.setOwner(createUser());
        projectBuilder.setUpdated(createDate("2018-06-20 12:34:56"));
        return projectBuilder.build();
    }

    private User createUser() {
        return new User(2, "admin", "admin@ucl.ac.uk");
    }

    private Date createDate(String date) throws ParseException  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(date);
    }
}
