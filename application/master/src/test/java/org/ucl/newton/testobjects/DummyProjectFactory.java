package org.ucl.newton.testobjects;

import org.ucl.newton.framework.Project;
import org.ucl.newton.framework.ProjectBuilder;
import org.ucl.newton.framework.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

public class DummyProjectFactory
{

    private HashMap<User, Collection<Project>> projects;
    private HashMap<User, Collection<Project>> starredProjects;


    public DummyProjectFactory() {
        User ziad = DummyUserFactory.createUserZiad();

        projects = new HashMap<>();
        Collection<Project> ziadProjects = new ArrayList<>();
        ziadProjects.add(createProjectGoshJiro());
        projects.put(ziad, ziadProjects);

        starredProjects = new HashMap<>();
        Collection<Project> ziadStarredProjects = new ArrayList<>();
        ziadStarredProjects.add(createProjectGoshJiro());
        starredProjects.put(ziad, ziadStarredProjects);
    }

    public Collection<Project> getProjects(User user) {
        return projects.get(user);
    }

    public Collection<Project> getStarredProjects(User user) {
        return starredProjects.get(user);
    }

    public Project createProjectGoshJiro() {
        User userZiad = DummyUserFactory.createUserZiad();
        return new Project(3, "gosh-jiro", "GOSH Project Jiro", "Project description",
                "default.png", new Date(), userZiad, new ArrayList<>(), new ArrayList<>());
    }

    public static Project createDummyProject(String identifier, String name) throws Exception {
        return createProject(identifier, name);
    }

    private static Project createProject(String identifier, String name) throws Exception {
        ProjectBuilder projectBuilder = new ProjectBuilder();
        projectBuilder.setIdentifier(identifier);
        projectBuilder.setName(name);
        projectBuilder.setDescription("project description");
        projectBuilder.setOwner(DummyUserFactory.createUserAdmin());
        projectBuilder.setUpdated(createDate("2018-06-20 12:34:56"));
        projectBuilder.setMembers(createMembersList());
        return projectBuilder.build();
    }

    private static Collection<User> createMembersList(){
        User userBlair = DummyUserFactory.createUserBlair();
        User userXiaolong = DummyUserFactory.createUserXiaolong();
        ArrayList<User> members = new ArrayList<>();
        members.add(userBlair);
        members.add(userXiaolong);
        return members;
    }

    private static Date createDate(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(date);
    }

}
