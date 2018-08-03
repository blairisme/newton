package org.ucl.newton.slave.service;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExperimentServiceTest
{
    @Test
    public void logTest() {
        Logger logger = LoggerFactory.getLogger(ExperimentServiceTest.class);



        logger.info("testing testing");
        logger.warn("testing testing");
        logger.error("testing testing");
    }
}
