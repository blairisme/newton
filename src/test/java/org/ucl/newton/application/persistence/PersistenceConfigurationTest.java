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

import javax.sql.DataSource;

public class PersistenceConfigurationTest
{
    @Test
    public void dataSourceTest() {
        PersistenceConfiguration configuration = new PersistenceConfiguration();
        DataSource dataSource = configuration.dataSource();
        Assert.assertNotNull(dataSource);
    }

    @Test
    public void dataSourceInitializerTest() {
        PersistenceConfiguration configuration = new PersistenceConfiguration();
        DataSourceInitializer dataSourceInitializer = configuration.dataSourceInitializer();
        Assert.assertNotNull(dataSourceInitializer);
    }

    @Test
    public void transationManagerTest() {
        SessionFactory sessionFactory = Mockito.mock(SessionFactory.class);
        PersistenceConfiguration configuration = new PersistenceConfiguration();
        HibernateTransactionManager transactionManager = configuration.transactionManager(sessionFactory);
        Assert.assertNotNull(transactionManager);
    }

    @Test
    public void sessionFactoryTest() {
        PersistenceConfiguration configuration = new PersistenceConfiguration();
        LocalSessionFactoryBean sessionFactory = configuration.sessionFactory();
        Assert.assertNotNull(sessionFactory);
    }
}
