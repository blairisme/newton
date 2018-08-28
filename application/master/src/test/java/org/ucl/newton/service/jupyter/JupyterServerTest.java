package org.ucl.newton.service.jupyter;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.ucl.newton.application.system.ApplicationPreferences;
import org.ucl.newton.framework.Experiment;
import org.ucl.newton.framework.User;
import org.ucl.newton.testobjects.DummyExperimentFactory;

import java.net.URI;

public class JupyterServerTest
{
    @Test
    public void getEditorUrlTest() throws Exception {
        User user = Mockito.mock(User.class);
        Mockito.when(user.getId()).thenReturn(123);

        ApplicationPreferences preferences = Mockito.mock(ApplicationPreferences.class);
        Mockito.when(preferences.getJupyterHost()).thenReturn("localhost");
        Mockito.when(preferences.getJupyterPort()).thenReturn(8000);

        Experiment experiment = DummyExperimentFactory.createExperiment("Experiment 1", "experiment-1");

        JupyterServer jupyterServer = new JupyterServer(preferences);
        URI editorUrl = jupyterServer.getEditorUrl(user, experiment);

        Assert.assertEquals("http", editorUrl.getScheme());
        Assert.assertEquals("localhost:8000", editorUrl.getAuthority());
        Assert.assertEquals("/login", editorUrl.getPath());
        Assert.assertTrue(editorUrl.getQuery().contains("experiment_id=experiment-1"));
        Assert.assertTrue(editorUrl.getQuery().contains("access_token=eyJhbGciOiJIUzI1NiJ9"));

    }

}
