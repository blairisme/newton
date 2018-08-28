/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.service.project;

import org.hibernate.LazyInitializationException;
import org.hibernate.NonUniqueResultException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ucl.newton.application.persistence.DeveloperPersistenceConfiguration;
import org.ucl.newton.framework.Project;
import org.ucl.newton.framework.User;
import org.ucl.newton.testobjects.DummyProjectFactory;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DeveloperPersistenceConfiguration.class})
@ActiveProfiles("development")
public class ProjectRepositoryTest
{
    @Inject
    private ProjectRepository repository;
    private final int fizzyoId = 1;
    private final int projCancerResId = 2;
    private String projectFizzyoIdentifier = "project-fizzyo";

    @Test
    public void addProjectTest() throws Exception {
        Project expected = DummyProjectFactory.createDummyProject("project-a", "project A");
        repository.addProject(expected);

        Project actual = repository.getProjectByIdentifier("project-a");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = NonUniqueResultException.class)
    public void testAddDuplicateProjects() throws Exception {
        Project project1 = DummyProjectFactory.createDummyProject("project-z", "project Z");
        Project project2 = DummyProjectFactory.createDummyProject("project-z", "project Z");
        repository.addProject(project1);
        repository.addProject(project2);
        repository.getProjectByIdentifier("project-z");
    }

    @Test
    public void getProjectsTest() {
        User user1 = new User(1, "user", "user@ucl.ac.uk", "default.jpg");
        Project fizzyo = repository.getProjectById(fizzyoId);
        Project cancerRes = repository.getProjectById(projCancerResId);
        List<Project> projects = repository.getProjects(user1);

        Assert.assertEquals(2, projects.size());
        Assert.assertTrue(projects.contains(fizzyo));
        Assert.assertTrue(projects.contains(cancerRes));
    }

    @Test
    public void testGetProjectEager() {
        Project project = repository.getProjectEagerlyByIdentifier(projectFizzyoIdentifier);
        Assert.assertEquals(5, project.getMembers().size());
    }

    @Test(expected = LazyInitializationException.class)
    public void testGetProjectLazy() {
        Project project = repository.getProjectByIdentifier(projectFizzyoIdentifier);
        project.getMembers().size();
    }
    

    @Test
    public void removeProjectTest() throws Exception {
        Project project = DummyProjectFactory.createDummyProject("project-b", "project B");

        repository.addProject(project);
        Project before = repository.getProjectByIdentifier("project-b");
        Assert.assertNotNull(before);

        repository.removeProject(before);
        Project after = repository.getProjectById(project.getId());
        Assert.assertNull(after);
    }

    @Test
    public void testUpdateProject() throws Exception {
        User userBlair = new User(3, "Blair Butterworth", "blair.butterworth.17@ucl.ac.uk", "default.jpg");
        Project newProject = DummyProjectFactory.createDummyProject("project-d", "Project d");
        repository.addProject(newProject);
        Project projectD = repository.getProjectEagerlyByIdentifier("project-d");
        Collection<User> projectMembers =  projectD.getMembers();
        Assert.assertTrue(projectMembers.contains(userBlair));

        projectMembers.remove(userBlair);
        projectD.setMembers(projectMembers);
        repository.updateProject(projectD);
        projectD = repository.getProjectEagerlyByIdentifier("project-d");
        Assert.assertFalse(projectD.getMembers().contains(userBlair));
    }

    @Test
    public void testProjectOwner() throws Exception {
        Project expected = DummyProjectFactory.createDummyProject("project-c", "project c");
        repository.addProject(expected);
        Project actual = repository.getProjectByIdentifier("project-c");
        Assert.assertEquals(expected.getOwner(), actual.getOwner());
    }

    @Test
    public void membersTest() {
        Project project = repository.getProjectEagerlyByIdentifier(projectFizzyoIdentifier);
        Collection<User> members = project.getMembers();
        Assert.assertEquals(5, members.size());
    }

    @Test
    public void testGetStaredProjects() {
        User user2 = new User(2, "admin", "admin@ucl.ac.uk", "default.jpg");
        User user1 = new User(1, "user", "user@ucl.ac.uk", "default.jpg");
        Project fizzyo = repository.getProjectById(1);
        List<Project> projects = repository.getProjectsStarredByUser(user2);

        Assert.assertEquals(6, projects.size());
        Assert.assertTrue(projects.contains(fizzyo));

        projects = repository.getProjectsStarredByUser(user1);
        Assert.assertEquals(2, projects.size());
    }

    @Test
    public void testGetStaredProjectsEmpty() {
        User userJohn = new User(6, "John Wilkie", "john.ilkie.17@ucl.ac.uk", "pp_1.jpg");
        List<Project> projects = repository.getProjectsStarredByUser(userJohn);
        Assert.assertEquals(0, projects.size());
    }

    @Test
    public void testGetStaredProjectsUnknownUser() {
        User unknown = new User(888, "Not known", "notknown@ucl.ac.uk", "default.jpg");
        List<Project> projects = repository.getProjectsStarredByUser(unknown);
        Assert.assertEquals(0, projects.size());
    }
/*
    private Project createProject(String identifier, String name) throws Exception {
        ProjectBuilder projectBuilder = new ProjectBuilder();
        projectBuilder.setIdentifier(identifier);
        projectBuilder.setName(name);
        projectBuilder.setDescription("project A Description");
        projectBuilder.setOwner(createUser());
        projectBuilder.setUpdated(createDate("2018-06-20 12:34:56"));
        projectBuilder.setMembers(createMembersList());
        return projectBuilder.build();
    }

    private User createUser() {
        return new User(2, "admin", "admin@ucl.ac.uk", "default.jpg");
    }

    private Collection<User> createMembersList(){
        User user3 = new User(3, "Blair Butterworth", "blair.butterworth.17@ucl.ac.uk", "default.jpg");
        User user4 = new User(4, "Xiaolong Chen", "xiaolong.chen@ucl.ac.uk", "default.jpg");
        ArrayList<User> members = new ArrayList<>();
        members.add(user3);
        members.add(user4);
        return members;
    }

    private Date createDate(String date) throws ParseException  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(date);
    }
    */
}
