package org.ucl.newton.testobjects;

import org.ucl.newton.framework.User;

import java.util.ArrayList;
import java.util.Collection;

public class DummyUserFactory {

    public static User createUserAdmin() {
        return new User(2, "admin", "admin@ucl.ac.uk", "pp_4.jpg"); //'admin', 'admin@ucl.ac.uk', 'pp_4.jpg'
    }

    public static User createUserBlair() {
        return new User(3, "Blair Butterworth", "blair.butterworth.17@ucl.ac.uk", "profile.jpg");
    }

    public static User createUserXiaolong() {
        return new User(4, "Xiaolong Chen", "xiaolong.chen@ucl.ac.uk", "pp_2.jpg");
    }

    public static User createUserZiad() {
        return new User(5, "Ziad Al Halabi", "ziad.halabi.17@ucl.ac.uk", "pp_3.jpg");
    }

    public static Collection<User> createListOfUsers(int n) {
        Collection<User> userList = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            userList.add(new User(i, "User " + i, "user"  + i + "@ucl.ac.uk", "pp_" + i + ".jpg"));
        }
        return userList;
    }
}
