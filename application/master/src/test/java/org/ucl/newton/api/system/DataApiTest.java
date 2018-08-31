package org.ucl.newton.api.system;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.ucl.newton.framework.DataPermission;
import org.ucl.newton.framework.User;
import org.ucl.newton.sdk.provider.DataProvider;
import org.ucl.newton.sdk.provider.DataSource;
import org.ucl.newton.service.data.DataPermissionService;
import org.ucl.newton.service.data.DataService;
import org.ucl.newton.service.data.DataStorageProvider;
import org.ucl.newton.service.user.UserService;
import org.ucl.newton.testobjects.DummyUserFactory;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DataApiTest {

    private MockMvc mockMvc;

    @Mock
    private DataService dataService;

    @Mock
    private DataPermissionService dataPermissionService;

    @Mock
    private UserService userService;

    @InjectMocks
    private DataApi dataApi;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(dataApi)
                .build();
    }

    @Test
    public void getdataSourceTest() throws Exception {
        String dataSourceId = "ds-id";
        DataSource dataSource = Mockito.mock(DataSource.class);
        DataProvider dataProvider = Mockito.mock(DataProvider.class);
        DataStorageProvider dataStorage = Mockito.mock(DataStorageProvider.class);
        Path dataPath = Mockito.mock(Path.class);

        when(dataSource.getProvider()).thenReturn(dataProvider);
        when(dataService.getDataSource(dataSourceId)).thenReturn(dataSource);
        when(dataService.getDataStorage(dataProvider)).thenReturn(dataStorage);
        when(dataStorage.getPath(dataSource)).thenReturn(dataPath);
        when(dataPath.toFile()).thenReturn(new File("/somepath/"));

        mockMvc.perform(get("/api/data/{dataSourceId}", dataSourceId))
                .andExpect(status().isOk());
    }

    @Test
    public void getPermissionTest() throws Exception {
        String dataSourceIdent = "ds-ident";
        User userZiad = DummyUserFactory.createUserZiad();
        DataPermission dataPermission = new DataPermission(1, userZiad, dataSourceIdent, new ArrayList<>());

        when(dataPermissionService.getPermissionByDataSourceName(dataSourceIdent)).thenReturn(dataPermission);

        mockMvc.perform(get("/api/data/permissions/{dsIdent}/permission", dataSourceIdent))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"id\":1,\"owner\":{\"id\":5,\"name\":\"Ziad Al Halabi\"," +
                        "\"email\":\"ziad.halabi.17@ucl.ac.uk\",\"image\":\"pp_3.jpg\"},\"dataSourceIdentifier\":\"ds-ident\"," +
                        "\"grantedPermissions\":[]}"));
    }

    @Test
    public void removeGrantedPermissionTest() throws Exception {
        String dataSourceIdent = "ds-ident";
        User userBlair = DummyUserFactory.createUserBlair();

        when(userService.getUserByEmail(userBlair.getEmail())).thenReturn(userBlair);

        mockMvc.perform(post("/api/data/permissions/{dsIdent}/remove?user={user}", dataSourceIdent, userBlair.getEmail()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("true"));
    }

    @Test
    public void removeGrantedPermissionTestFailed() throws Exception {
        String dataSourceIdent = "ds-ident";
        User userBlair = DummyUserFactory.createUserBlair();

        when(userService.getUserByEmail(userBlair.getEmail())).thenThrow(new NullPointerException());

        mockMvc.perform(post("/api/data/permissions/{dsIdent}/remove?user={user}", dataSourceIdent, userBlair.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void addGrantedPermissionTest() throws Exception {
        String dataSourceIdent = "ds-ident";
        User userBlair = DummyUserFactory.createUserBlair();

        when(userService.getUserByEmail(userBlair.getEmail())).thenReturn(userBlair);

        mockMvc.perform(post("/api/data/permissions/{dsIdent}/add?user={user}", dataSourceIdent, userBlair.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void addGrantedPermissionTestFailed() throws Exception {
        String dataSourceIdent = "ds-ident";
        User userBlair = DummyUserFactory.createUserBlair();

        when(userService.getUserByEmail(userBlair.getEmail())).thenThrow(new NullPointerException());

        mockMvc.perform(post("/api/data/permissions/{dsIdent}/add?user={user}", dataSourceIdent, userBlair.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

}
