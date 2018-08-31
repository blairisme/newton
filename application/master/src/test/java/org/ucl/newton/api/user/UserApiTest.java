package org.ucl.newton.api.user;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ucl.newton.framework.Credential;
import org.ucl.newton.framework.User;
import org.ucl.newton.framework.UserRole;
import org.ucl.newton.service.authentication.AuthenticationService;
import org.ucl.newton.service.authentication.UnknownRoleException;
import org.ucl.newton.service.user.UserService;
import org.ucl.newton.testobjects.DummyUserFactory;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserApiTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private UserApi userApi;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userApi)
                .build();
    }

    @Test
    public void getUsersTest() throws Exception {
        String request = "zia";
        Collection<User> matchingUsers = new ArrayList<>();
        matchingUsers.add(DummyUserFactory.createUserZiad());

        when(userService.getUsers(request)).thenReturn(matchingUsers);

        mockMvc.perform(get("/api/users?matching={mathing}", request))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":5,\"name\":\"Ziad Al Halabi\"," +
                        "\"email\":\"ziad.halabi.17@ucl.ac.uk\",\"image\":\"pp_3.jpg\"}]"));
    }

    @Test
    public void getPrivilegedUsersTest() throws Exception {
        String request = "use";
        Collection<User> matchingUsers = DummyUserFactory.createListOfUsers(3);

        when(userService.getUsers(request)).thenReturn(matchingUsers);
        for(User user: matchingUsers) {
            if(user.getId() == 0) {
                when(authenticationService.getCredentials(user)).thenReturn(new Credential(1, user.getEmail(), "password", UserRole.ADMIN));
            } else if(user.getId() == 1) {
                when(authenticationService.getCredentials(user)).thenReturn(new Credential(2, user.getEmail(), "password", UserRole.USER));
            } else {
                when(authenticationService.getCredentials(user)).thenReturn(new Credential(3, user.getEmail(), "password", UserRole.USER));
            }
        }

        mockMvc.perform(get("/api/privilegedusers?matching={mathing}", request))
                .andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":0,\"name\":\"User 0\"," +
                        "\"email\":\"user0@ucl.ac.uk\",\"image\":\"pp_0.jpg\"}]"));
    }

    @Test
    public void getUserRoleTest() throws Exception {
        User userBlair = DummyUserFactory.createUserBlair();
        Credential credentialBlair = new Credential(1, userBlair.getEmail(), "password", UserRole.ORGANISATIONLEAD);

        when(userService.getUserByEmail(userBlair.getEmail())).thenReturn(userBlair);
        when(authenticationService.getCredentials(userBlair)).thenReturn(credentialBlair);

        mockMvc.perform(get("/api/userrole?username={name}", userBlair.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().string("\"ORGANISATIONLEAD\""));
    }

    @Test
    public void setUserRoleErrorTest() throws Exception, UnknownRoleException {
        User userJohn = DummyUserFactory.createUserJohn();

        when(authenticationService.changeRole(userJohn.getName(), "ADMINS")).thenThrow(new UnknownRoleException("ADMINS"));

        mockMvc.perform(post("/api/updaterole?username={name}&role={role}", userJohn.getEmail(), "ADMINS"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

}
