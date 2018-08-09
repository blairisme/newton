package org.ucl.newton.fizzyo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ucl.newton.sdk.plugin.PluginVisualization;
import org.ucl.newton.sdk.provider.BasicDataSource;
import org.ucl.newton.sdk.provider.DataSource;

public class FizzyoDataProviderTest {
    private FizzyoDataProvider provider;

    @Before
    public void setUp() {
        provider = new FizzyoDataProvider();
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
    public void getWeatherDataSource(){
        DataSource dataSource = provider.getFizzyoDataSource();
        Assert.assertTrue(dataSource instanceof BasicDataSource);
    }

    @Test
    public void getDataSourcesTest(){
        Assert.assertEquals(1,provider.getDataSources().size());
    }
}
