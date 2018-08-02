package org.ucl.newton.ui;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.ucl.newton.application.system.ApplicationStorage;

import java.nio.file.Paths;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PluginControllerTest {
    private ApplicationStorage applicationStorage;
    private PluginController controller;
    @Before
    public void setUp()throws Exception{
        applicationStorage = mock(ApplicationStorage.class);
        controller = new PluginController(applicationStorage);
        when(applicationStorage.getRootPath()).thenReturn(Paths.get(System.getProperty("user.home")).resolve(".newton").toString());

    }
    @Test
    public void getDataTest(){
        Assert.assertEquals("plugin/FizzyoData",controller.getData(new ModelMap()));
    }
}
