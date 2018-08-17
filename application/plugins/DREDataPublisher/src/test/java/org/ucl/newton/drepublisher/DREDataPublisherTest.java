package org.ucl.newton.drepublisher;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ucl.newton.sdk.plugin.PluginHostContext;
import org.ucl.newton.sdk.plugin.PluginHostStorage;
import org.ucl.newton.sdk.plugin.PluginVisualization;

import java.io.FileInputStream;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DREDataPublisherTest {
    private DREDataPublisher publisher;
    private PluginHostContext context;
    @Before
    public void setUp(){
        PluginHostStorage storage = mock(PluginHostStorage.class);
        context = mock(PluginHostContext.class);
        when(context.getStorage()).thenReturn(storage);
        publisher = new DREDataPublisher();

    }
    @Test
    public void getIdentifierTest() {
        Assert.assertEquals("newton-DRE",publisher.getIdentifier());
    }
    @Test
    public void getNameTest() {
        PluginVisualization visualization = publisher.getVisualization();
        Assert.assertEquals("DRE Data publisher",visualization.getName());
    }
    @Test
    public void getDescriptionTest() {
        PluginVisualization visualization = publisher.getVisualization();
        Assert.assertEquals("Publish data into DRE via FTP",visualization.getDescription());
    }
    @Test
    public void setContextTest(){
        publisher.setContext(context);
    }
    @Test
    public void startTest(){
        publisher.start("");
    }
}
