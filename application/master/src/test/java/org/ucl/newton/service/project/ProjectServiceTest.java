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
import org.mockito.Mockito;
import org.ucl.newton.framework.Project;
import org.ucl.newton.framework.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectServiceTest
{
    private ProjectRepository repository;
    private ProjectService service;
    private String goshJiroIdentifier = "gosh-jiro";
    private int goshJiroId = 3;
    private Project goshJiro;
    private Project newProject;
    private User userBlair;
    private User userZiad;

    @Before
    public void setUp() {
        repository = Mockito.mock(ProjectRepository.class);
        service = new ProjectService(repository);
        goshJiro = new Project(3, "gosh-jiro", "GOSH Project Jiro", "Project description",
                "default.png", new Date(), new User(), new ArrayList<>(), new ArrayList<>());
        newProject = new Project(99, "new-project", "New Project", "Project description",
                "image", new Date(), new User(), new ArrayList<>(), new ArrayList<>());
        userBlair = new User(3, "Blair Butterworth", "blair.butterworth.17@ucl.ac.uk", "profile.jpg");
        userZiad = new User(5, "Ziad Al Halabi", "ziad.halabi.17@ucl.ac.uk", "default.jpg");
    }

    @Test
    public void testGetProjectById() {
        Mockito.when(repository.getProjectById(goshJiroId)).thenReturn(goshJiro);
        Assert.assertEquals(goshJiro, service.getProjectById(goshJiroId));
    }

    @Test(expected = UnknownProjectException.class)
    public void testProjectByIdUnknown() {
        Mockito.when(repository.getProjectById(goshJiroId)).thenReturn(null);
        service.getProjectById(999);
    }

    @Test
    public void testGetProjectByIdentifierEager() {
        Mockito.when(repository.getProjectEagerlyByIdentifier(goshJiroIdentifier)).thenReturn(goshJiro);
        Assert.assertEquals(goshJiro, service.getProjectByIdentifier(goshJiroIdentifier, true));
    }

    @Test
    public void testGetProjectByIdentifierLazy() {
        Mockito.when(repository.getProjectByIdentifier(goshJiroIdentifier)).thenReturn(goshJiro);
        Assert.assertEquals(goshJiro, service.getProjectByIdentifier(goshJiroIdentifier, false));
    }

    @Test(expected = UnknownProjectException.class)
    public void testGetProjectByIdentifierUnknown() {
        Mockito.when(repository.getProjectByIdentifier(goshJiroIdentifier)).thenReturn(null);
        service.getProjectByIdentifier("unknown", true);
    }

    @Test
    public void testGetProjectsForUser() {
        Mockito.when(repository.getProjects(userBlair)).thenReturn(getProjectList());
        Assert.assertEquals(getProjectList(), service.getProjects(userBlair));
    }

    @Test
    public void testGetProjectsForUnknownUser() {
        User userUnknown = new User(999, "Unknown", "unknown@ucl.ac.uk", "profile.jpg");
        Mockito.when(repository.getProjects(userUnknown)).thenReturn(new ArrayList<>());
        Assert.assertEquals(0, service.getProjects(userUnknown).size());
    }

    @Test
    public void testGetProjectForUserEmpty() {
        User userWithNoProjects = new User(10, "User", "user@ucl.ac.uk", "image");
        Mockito.when(repository.getProjects(userWithNoProjects)).thenReturn(new ArrayList<>());
        Assert.assertEquals(0, service.getProjects(userWithNoProjects).size());
    }

    @Test
    public void testAddProject() {
        Mockito.when(repository.getProjectByIdentifier("new-project")).thenReturn(null);
        try {
            service.getProjectByIdentifier("new-project", false);
        } catch (UnknownProjectException e) {
            Assert.assertTrue(true);
        }

        Mockito.when(repository.getProjectByIdentifier("new-project")).thenReturn(newProject);
        service.addProject(newProject);
        Project actual = service.getProjectByIdentifier("new-project", false);
        Assert.assertEquals(newProject, actual);
    }

    @Test
    public void testAddAMemberToAProject() {
        List<User> jiroMembers = new ArrayList<>();
        goshJiro.setMembers(jiroMembers);
        Mockito.when(repository.getProjectEagerlyByIdentifier(goshJiroIdentifier)).thenReturn(goshJiro);
        Assert.assertFalse(service.getProjectByIdentifier(goshJiroIdentifier, true).getMembers().contains(userBlair));
        service.addMemberToProject("gosh-jiro", userBlair);
        jiroMembers.add(userBlair);
        Assert.assertTrue(service.getProjectByIdentifier(goshJiroIdentifier, true).getMembers().contains(userBlair));
    }

    @Test
    public void testRemoveAMemberFromAProject() {
        List<User> jiroMembers = new ArrayList<>();
        jiroMembers.add(userZiad);
        goshJiro.setMembers(jiroMembers);
        Mockito.when(repository.getProjectEagerlyByIdentifier(goshJiroIdentifier)).thenReturn(goshJiro);
        Assert.assertTrue(service.getProjectByIdentifier(goshJiroIdentifier, true).getMembers().contains(userZiad));
        service.removeMemberFromProject("gosh-jiro", userZiad);
        jiroMembers.remove(userZiad);
        Assert.assertFalse(service.getProjectByIdentifier(goshJiroIdentifier, true).getMembers().contains(userZiad));
    }

    @Test
    public void testGetStarredProjectsForUser() {
        List<Project> starredProjects = new ArrayList<>();
        starredProjects.add(goshJiro);
        Mockito.when(repository.getProjectsStarredByUser(userBlair)).thenReturn(starredProjects);
        Assert.assertTrue(service.getStarredProjects(userBlair).contains(goshJiro));
        Assert.assertEquals(1, service.getStarredProjects(userBlair).size());
    }

    @Test
    public void testGetStarredProjectsForUserEmpty() {
        List<Project> starredProjects = new ArrayList<>();
        Mockito.when(repository.getProjectsStarredByUser(userZiad)).thenReturn(starredProjects);
        Assert.assertEquals(0, service.getStarredProjects(userBlair).size());
    }

    @Test
    public void testPersistStarringAProject() {
        List<User> membersThatStar = new ArrayList<>();
        goshJiro.setMembersThatStar(membersThatStar);
        Mockito.when(repository.getProjectEagerlyByIdentifier(goshJiroIdentifier)).thenReturn(goshJiro);
        Assert.assertFalse(service.getProjectByIdentifier(goshJiroIdentifier, true).getMembersThatStar().contains(userBlair));
        membersThatStar.add(userBlair);
        service.addStar("gosh-jiro", userBlair);
        Assert.assertTrue(service.getProjectByIdentifier(goshJiroIdentifier, true).getMembersThatStar().contains(userBlair));
    }

    @Test
    public void testUnstarringAProject() {
        List<User> membersThatStar = new ArrayList<>();
        membersThatStar.add(userZiad);
        goshJiro.setMembersThatStar(membersThatStar);
        Mockito.when(repository.getProjectEagerlyByIdentifier(goshJiroIdentifier)).thenReturn(goshJiro);
        Assert.assertTrue(service.getProjectByIdentifier(goshJiroIdentifier, true).getMembersThatStar().contains(userZiad));
        service.removeStar("gosh-jiro", userZiad);
        membersThatStar.remove(userZiad);
        goshJiro = service.getProjectByIdentifier(goshJiroIdentifier, true);
        Assert.assertFalse(service.getProjectByIdentifier(goshJiroIdentifier, true).getMembersThatStar().contains(userZiad));
    }

    private List<Project> getProjectList() {
        List<Project> projectList = new ArrayList<>();
        projectList.add(goshJiro);
        return projectList;
    }

}
