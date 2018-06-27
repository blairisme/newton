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
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.framework.Project;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@Transactional
@ActiveProfiles("development")
public class ProjectRepositoryTest
{
    @Inject
    private ProjectRepository repository;

    @Test
    public void getProjectTest() throws Exception {
        Project expected = new Project("project-fizzyo", "Project Fizzyo", "Project Fizzyo Description", getDate("2018-06-20 12:34:56"));
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
        Project expected = new Project("project-a", "Project A", "Project A Description", getDate("2018-06-20 12:34:56"));
        repository.addProject(expected);
        Project actual = repository.getProject("project-a");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void removeProjectTest() throws Exception {
        Project project = new Project("project-b", "Project B", "Project B Description", getDate("2018-06-20 12:34:56"));

        repository.addProject(project);
        Project before = repository.getProject("project-b");
        Assert.assertNotNull(before);

        repository.removeProject(before);
        Project after = repository.getProject("project-b");
        Assert.assertNull(after);
    }

    private Date getDate(String date) throws ParseException  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(date);
    }
}
