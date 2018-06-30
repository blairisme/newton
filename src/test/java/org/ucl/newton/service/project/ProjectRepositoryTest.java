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


    @Test
    public void addProjectTest() throws Exception {
        User owner = new User(2, "admin", "admin@ucl.ac.uk");
        Date updated = getDate("2018-06-20 12:34:56");

        Project expected = new Project("project-a", "project A", "project A Description", updated, owner);
        repository.addProject(expected);

        Project actual = repository.getProjectByLink("project-a");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getProjectsTest() {
        List<Project> projects = repository.getProjects(0, 20);
        Assert.assertEquals(3, projects.size());
    }

    @Test
    public void removeProjectTest() throws Exception {
        User owner = new User(2, "admin", "admin@ucl.ac.uk");
        Date updated = getDate("2018-06-20 12:34:56");
        Project project = new Project("project-b", "project B", "project B Description", updated, owner);

        repository.addProject(project);
        Project before = repository.getProjectByLink("project-b");
        Assert.assertNotNull(before);

        repository.removeProject(before);
        Project after = repository.getProjectById(project.getId());
        Assert.assertNull(after);
    }

    @Test
    public void testProjectOwner() throws Exception {
        User owner = new User(2, "admin", "admin@ucl.ac.uk");
        Date updated = getDate("2018-06-20 12:34:56");
        Project expected = new Project("project-c", "project c", "Project C Description", updated, owner);

        repository.addProject(expected);
        Project actual = repository.getProjectByLink("project-c");
        Assert.assertEquals(expected.getOwner(), actual.getOwner());
    }

    private Date getDate(String date) throws ParseException  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(date);
    }
}
