package org.ucl.newton.drepublisher;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ucl.newton.sdk.publisher.FTPConfig;

public class DREDataPublisherTest {
    private DREDataPublisher publisher;
    @Before
    public void setUp(){
        publisher = new DREDataPublisher();
        FTPConfig config = new FTPConfig("1","2","3",4);
        publisher.setConfig(config);
    }
    @Test
    public void getConfigNameTest() {
        Assert.assertEquals("DREFTPConfig.json",publisher.getConfigName());
    }
    @Test
    public void getConfigClassTest() {
        Assert.assertEquals(FTPConfig.class,publisher.getConfigClass());
    }
    @Test
    public void getIdentifierTest() {
        Assert.assertEquals("newton-DRE",publisher.getIdentifier());
    }
    @Test
    public void getNameTest() {
        Assert.assertEquals("DRE Data publisher",publisher.getName());
    }
    @Test
    public void getDescriptionTest() {
        Assert.assertEquals("Publish data into DRE via FTP",publisher.getDescription());
    }
    @Test
    public void startTest(){
        publisher.start("");
    }
}
