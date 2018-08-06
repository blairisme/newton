/*
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.newton.application.persistence;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.ucl.newton.application.system.ApplicationPreferences;
import org.ucl.newton.application.system.ApplicationStorage;

import javax.sql.DataSource;
import java.nio.file.Paths;

public class PersistenceConfigurationTest
{
    @Test
    public void dataSourceTest() {
        PersistenceConfiguration configuration = getPersistenceConfiguration();
        DataSource dataSource = configuration.dataSource();
        Assert.assertNotNull(dataSource);
    }

    @Test
    public void dataSourceInitializerTest() {
        PersistenceConfiguration configuration = getPersistenceConfiguration();
        DataSourceInitializer dataSourceInitializer = configuration.dataSourceInitializer();
        Assert.assertNotNull(dataSourceInitializer);
    }

    @Test
    public void transationManagerTest() {
        SessionFactory sessionFactory = Mockito.mock(SessionFactory.class);
        PersistenceConfiguration configuration = getPersistenceConfiguration();
        HibernateTransactionManager transactionManager = configuration.transactionManager(sessionFactory);
        Assert.assertNotNull(transactionManager);
    }

    @Test
    public void sessionFactoryTest() {
        PersistenceConfiguration configuration = getPersistenceConfiguration();
        LocalSessionFactoryBean sessionFactory = configuration.sessionFactory();
        Assert.assertNotNull(sessionFactory);
    }

    private PersistenceConfiguration getPersistenceConfiguration() {
        ApplicationStorage applicationStorage = Mockito.mock(ApplicationStorage.class);
        Mockito.when(applicationStorage.getIndexDirectory()).thenReturn(Paths.get("/test/foo"));

        ApplicationPreferences applicationPreferences = Mockito.mock(ApplicationPreferences.class);
        return new PersistenceConfiguration(applicationPreferences, applicationStorage);
    }
}
