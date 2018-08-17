package org.ucl.newton.ui;

import org.junit.Test;

import java.util.Arrays;

//package org.ucl.newton.ui;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.ui.ModelMap;
//import org.ucl.newton.application.system.ApplicationStorage;
//import org.ucl.newton.service.publisher.PublisherService;
//
//import java.nio.file.Paths;
//
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//public class PluginControllerTest {
//    private ApplicationStorage applicationStorage;
//    private PluginController controller;
//    private PublisherService service;
//    @Before
//    public void setUp()throws Exception{
//        applicationStorage = mock(ApplicationStorage.class);
//        service = mock(PublisherService.class);
//        controller = new PluginController(applicationStorage,service);
//        when(applicationStorage.getRootPath()).thenReturn(Paths.get(System.getProperty("user.home")).resolve(".newton").toString());
//
//    }
//    @Test
//    public void getDataTest(){
//        Assert.assertEquals("plugin/FizzyoData",controller.getData(new ModelMap()));
//    }
//}
public class PluginControllerTest {
    @Test
    public void justTest(){
        String a = "aasda_sa ahdadka_ashdaskhda";

        System.out.println(Arrays.asList(a.split("_")));
    }
}
