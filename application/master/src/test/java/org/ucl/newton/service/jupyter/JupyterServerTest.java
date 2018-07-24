package org.ucl.newton.service.jupyter;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.newton.framework.User;

import java.net.URI;

public class JupyterServerTest
{
    @Test
    public void getEditorUrlTest() {
        User user = Mockito.mock(User.class);
        Mockito.when(user.getId()).thenReturn(123);

        JupyterServer jupyterServer = new JupyterServer();
        URI editorUrl = jupyterServer.getEditorUrl(user, "456");

        Assert.assertEquals("http", editorUrl.getScheme());
        Assert.assertEquals("localhost:8000", editorUrl.getAuthority());
        Assert.assertEquals("/login", editorUrl.getPath());
        Assert.assertTrue(editorUrl.getQuery().startsWith("experiment_id=456"));
        Assert.assertTrue(editorUrl.getQuery().contains("access_token=eyJhbGciOiJIUzI1NiJ9"));

    }

}
