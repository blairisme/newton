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
import org.ucl.newton.framework.UserRole;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private final int fizzyoId = 1;
    private final int projCancerResId = 2;

    @Test
    public void addProjectTest() throws Exception {
        Project expected = createProject("project-a", "project A");
        repository.addProject(expected);

        Project actual = repository.getProjectByIdentifier("project-a");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getProjectsTest() {
        User user1 = new User(1, "user", "user@ucl.ac.uk", UserRole.USER);
        Project fizzyo = repository.getProjectById(fizzyoId);
        Project cancerRes = repository.getProjectById(projCancerResId);
        List<Project> projects = repository.getProjects(user1);

        Assert.assertEquals(2, projects.size());
        Assert.assertTrue(projects.contains(fizzyo));
        Assert.assertTrue(projects.contains(cancerRes));
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

    @Test
    public void testAddMemberToProject() {
        int projCancerResId = 2;
        Project projCancerRes = repository.getProjectById(projCancerResId);
        Collection<User> memberList= projCancerRes.getMembers();
        User newUser = new User(6, "John Wilkie", "john.wilkie.17@ucl.ac.uk", UserRole.ADMIN);

        Assert.assertEquals(2, memberList.size());
        Assert.assertFalse(memberList.contains(newUser));

        addUserToProjectMembers(newUser, projCancerRes);
        projCancerRes = repository.getProjectById(projCancerResId);

        Assert.assertEquals(3, projCancerRes.getMembers().size());
        Assert.assertTrue(projCancerRes.getMembers().contains(newUser));
    }

    @Test
    public void testRemoveMemberFromProject() {

        Project projCancerRes = repository.getProjectById(projCancerResId);
        Collection<User> memberList;
        User user5 = new User(5, "Ziad Al Halabi", "ziad.halabi.17@ucl.ac.uk", UserRole.ADMIN);

        addUserToProjectMembers(user5, projCancerRes);
        projCancerRes = repository.getProjectById(projCancerResId);
        memberList= projCancerRes.getMembers();
        Assert.assertTrue(memberList.contains(user5));

        memberList.remove(user5);
        projCancerRes.setMembers(memberList);
        repository.updateProject(projCancerRes);
        projCancerRes = repository.getProjectById(projCancerResId);
        memberList= projCancerRes.getMembers();

        Assert.assertFalse(memberList.contains(user5));
    }

    private void addUserToProjectMembers(User user, Project project) {
        Collection<User> memberList = project.getMembers();
        memberList.add(user);
        project.setMembers(memberList);
        repository.updateProject(project);
    }

    @Test
    public void testGetStaredProjects() {
        User user2 = new User(2, "admin", "admin@ucl.ac.uk", UserRole.ADMIN);
        User user1 = new User(1, "user", "user@ucl.ac.uk", UserRole.USER);
        Project fizzyo = repository.getProjectById(1);
        List<Project> projects = repository.getProjectsStarredByUser(user2);

        Assert.assertEquals(1, projects.size());
        Assert.assertTrue(projects.contains(fizzyo));

        projects = repository.getProjectsStarredByUser(user1);
        Assert.assertEquals(2, projects.size());
    }

    @Test
    public void testStarAProject() {
        User user4 = new User(4, "Xiaolong Chen", "xiaolong.chen@ucl.ac.uk", UserRole.ADMIN);
        Project fizzyo = repository.getProjectById(fizzyoId);
        Collection<User> starredMembers = fizzyo.getMembersThatStar();
        Assert.assertEquals(2, starredMembers.size());
        Assert.assertFalse(starredMembers.contains(user4));

        starAProject(user4, fizzyo);

        fizzyo = repository.getProjectById(fizzyoId);
        starredMembers = fizzyo.getMembersThatStar();

        Assert.assertEquals(3, starredMembers.size());
        Assert.assertTrue(starredMembers.contains(user4));
    }

    @Test
    public void testUnstarAProject() {
        User user5 = new User(5, "Ziad Al Halabi", "ziad.halabi.17@ucl.ac.uk", UserRole.ADMIN);
        Project aidsRes = repository.getProjectById(3);
        starAProject(user5, aidsRes);

        aidsRes = repository.getProjectById(3);
        Collection<User> starredMembers = aidsRes.getMembersThatStar();
        Assert.assertEquals(1, starredMembers.size());
        Assert.assertTrue(starredMembers.contains(user5));

        starredMembers.remove(user5);
        aidsRes.setMembersThatStar(starredMembers);
        repository.updateProject(aidsRes);
        aidsRes = repository.getProjectById(3);
        starredMembers = aidsRes.getMembersThatStar();

        Assert.assertEquals(0, starredMembers.size());
        Assert.assertFalse(starredMembers.contains(user5));
    }

    private void starAProject(User user, Project project){
        Collection<User> starredMembers = project.getMembersThatStar();
        starredMembers.add(user);
        project.setMembersThatStar(starredMembers);
        repository.updateProject(project);
    }

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
        return new User(2, "admin", "admin@ucl.ac.uk", UserRole.ADMIN);
    }

    private Collection<User> createMembersList(){
        User user3 = new User(3, "Blair Butterworth", "blair.butterworth.17@ucl.ac.uk", UserRole.ADMIN);
        User user4 = new User(4, "Xiaolong Chen", "xiaolong.chen@ucl.ac.uk", UserRole.ADMIN);
        ArrayList<User> members = new ArrayList<>();
        members.add(user3);
        members.add(user4);
        return members;
    }

    private Date createDate(String date) throws ParseException  {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(date);
    }
}
