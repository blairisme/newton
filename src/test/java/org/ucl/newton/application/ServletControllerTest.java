package org.ucl.newton.application;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.ui.ModelMap;

public class ServletControllerTest
{
    @Test
    public void testRoot(){
        ServletController controller = new ServletController();
        Assert.assertEquals("redirect:/home", controller.root(new ModelMap()));
    }

    @Test
    public void testHome(){
        ServletController controller = new ServletController();
        Assert.assertEquals("home", controller.home("Blair", new ModelMap()));
    }

    @Test
    public void testSignin(){
        ServletController controller = new ServletController();
        Assert.assertEquals("signin", controller.signin(new ModelMap()));
    }
}
