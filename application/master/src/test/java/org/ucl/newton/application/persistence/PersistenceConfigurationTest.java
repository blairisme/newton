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
import org.ucl.newton.application.webapp.ApplicationPreferences;

import javax.sql.DataSource;

public class PersistenceConfigurationTest
{
    @Test
    public void dataSourceTest() {
        ApplicationPreferences applicationPreferences = Mockito.mock(ApplicationPreferences.class);
        PersistenceConfiguration configuration = new PersistenceConfiguration(applicationPreferences);
        DataSource dataSource = configuration.dataSource();
        Assert.assertNotNull(dataSource);
    }

    @Test
    public void dataSourceInitializerTest() {
        ApplicationPreferences applicationPreferences = Mockito.mock(ApplicationPreferences.class);
        PersistenceConfiguration configuration = new PersistenceConfiguration(applicationPreferences);
        DataSourceInitializer dataSourceInitializer = configuration.dataSourceInitializer();
        Assert.assertNotNull(dataSourceInitializer);
    }

    @Test
    public void transationManagerTest() {
        ApplicationPreferences applicationPreferences = Mockito.mock(ApplicationPreferences.class);
        SessionFactory sessionFactory = Mockito.mock(SessionFactory.class);
        PersistenceConfiguration configuration = new PersistenceConfiguration(applicationPreferences);
        HibernateTransactionManager transactionManager = configuration.transactionManager(sessionFactory);
        Assert.assertNotNull(transactionManager);
    }

    @Test
    public void sessionFactoryTest() {
        ApplicationPreferences applicationPreferences = Mockito.mock(ApplicationPreferences.class);
        PersistenceConfiguration configuration = new PersistenceConfiguration(applicationPreferences);
        LocalSessionFactoryBean sessionFactory = configuration.sessionFactory();
        Assert.assertNotNull(sessionFactory);
    }
}
