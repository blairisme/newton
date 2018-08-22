package org.ucl.newton.fizzyo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ucl.newton.sdk.plugin.PluginHostContext;
import org.ucl.newton.sdk.plugin.PluginHostStorage;
import org.ucl.newton.sdk.plugin.PluginVisualization;
import org.ucl.newton.sdk.provider.BasicDataSource;
import org.ucl.newton.sdk.provider.DataSource;

import java.io.IOException;

import static java.lang.Thread.sleep;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FizzyoDataProviderTest {
    private FizzyoDataProvider provider;
    private PluginHostContext context;
    @Before
    public void setUp() {

        provider = new FizzyoDataProvider();
        context = () -> {
            PluginHostStorage storage = mock(PluginHostStorage.class);
            try {
                when(storage.getInputStream(anyString())).thenReturn(getClass().getResourceAsStream("/configuration/FizzyoConfiguration"));
            }catch (IOException e){
                e.printStackTrace();
            }
            return storage;
        };
    }

    @Test
    public void getIdentifierTest() {
        Assert.assertEquals("newton-fizzyo", provider.getIdentifier());
    }

    @Test
    public void getDescriptionTest() {
        PluginVisualization visualization = provider.getVisualization();
        Assert.assertEquals("Obtains data from the Fizzyo cloud.", visualization.getDescription());
    }

    @Test
    public void getNameTest() {
        PluginVisualization visualization = provider.getVisualization();
        Assert.assertEquals("Fizzyo Data Provider", visualization.getName());
    }

    @Test
    public void getFizzyoDataSource(){
        DataSource dataSource = provider.getFizzyoDataSource();
        Assert.assertTrue(dataSource instanceof BasicDataSource);
    }

    @Test
    public void getDataSourcesTest(){
        Assert.assertEquals(6,provider.getDataSources().size());
    }

    @Test
    public void startTest() throws Exception{
        provider.setContext(context);
        provider.start();
    }
}
