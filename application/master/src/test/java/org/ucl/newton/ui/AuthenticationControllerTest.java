/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.ui;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ucl.newton.api.user.UserDto;
import org.ucl.newton.framework.User;
import org.ucl.newton.service.authentication.AuthenticationService;
import org.ucl.newton.service.user.UserService;
import org.ucl.newton.testobjects.DummyUserFactory;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthenticationControllerTest
{

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationService authService;

    @InjectMocks
    private AuthenticationController controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void loginTest() throws Exception {
        mockMvc.perform(get("/login")
                .param("error", "false"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("error", false))
                .andExpect(view().name("auth/login"));
    }

    @Test
    public void registerTest() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/signup"));
    }

    @Test
    public void accessDeniedtest() throws Exception {
        mockMvc.perform(get("/access_denied"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/denied"));
    }

    @Test
    public void registerUserTest() throws Exception {
        UserDto userDto = new UserDto();
        User userZiad = DummyUserFactory.createUserZiad();
        userDto.setEmail(userZiad.getEmail());
        userDto.setFullName(userZiad.getName());
        userDto.setPassword("password");

        when(userService.getUserByEmail(userDto.getEmail())).thenReturn(null, userZiad);

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED).content(EntityUtils.toString(
                        new UrlEncodedFormEntity(Arrays.asList(
                                new BasicNameValuePair("fullName", userDto.getFullName()),
                                new BasicNameValuePair("password", userDto.getPassword()),
                                new BasicNameValuePair("email", userDto.getEmail()))))))
                .andExpect(status().is(302))
                .andExpect(redirectedUrl("/projects"));
    }

    @Test
    public void registerUserInSystemTest() throws Exception {
        UserDto userDto = new UserDto();
        User userZiad = DummyUserFactory.createUserZiad();
        userDto.setEmail(userZiad.getEmail());
        userDto.setFullName(userZiad.getName());
        userDto.setPassword("password");

        when(userService.getUserByEmail(userDto.getEmail())).thenReturn(userZiad);

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED).content(EntityUtils.toString(
                        new UrlEncodedFormEntity(Arrays.asList(
                        new BasicNameValuePair("fullName", userDto.getFullName()),
                        new BasicNameValuePair("password", userDto.getPassword()),
                        new BasicNameValuePair("email", userDto.getEmail()))))))
                .andExpect(status().is(200))
                .andExpect(view().name("auth/signup"));
    }

}
