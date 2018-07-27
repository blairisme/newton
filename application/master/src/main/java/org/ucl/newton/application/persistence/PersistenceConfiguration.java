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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.ucl.newton.application.system.ApplicationPreferences;
import org.ucl.newton.common.file.SystemUtils;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Instances of this class configure the systems data persistence
 * infrastructure. Specifically this class establishes a a connection to a
 * local MySQL database instance and provides this to the Hibernate framework,
 * through which queries will be made and data accessed.
 *
 * @author Blair Butterworth
 */
@Configuration
@Profile("production")
@EnableTransactionManagement
@ComponentScan(basePackages = {"org.ucl.newton.service", "org.ucl.newton.framework"})
@SuppressWarnings("unused")
public class PersistenceConfiguration
{
    private ApplicationPreferences applicationPreferences;

    @Inject
    public PersistenceConfiguration(ApplicationPreferences applicationPreferences) {
        this.applicationPreferences = applicationPreferences;
    }

    @Bean
    public DataSource dataSource() {
        String host = applicationPreferences.getDatabaseHost();
        String port = applicationPreferences.getDatabasePort();

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://" + host + ":" + port + "/newton");
        dataSource.setUsername(applicationPreferences.getDatabaseUser());
        dataSource.setPassword(applicationPreferences.getDatabasePassword());
        dataSource.setConnectionProperties(mysqlProperties());
        return dataSource;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/sql/schema.sql"));
        resourceDatabasePopulator.addScript(new ClassPathResource("/sql/production.sql"));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource());
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("org.ucl.newton.framework");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    @Inject
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.search.default.directory_provider", "filesystem");
        properties.put("hibernate.search.default.indexBase", getIndexPath());
        return properties;
    }

    private String getIndexPath() {
        Path indexPath = SystemUtils.newTempDirectory("index");
        return indexPath.toString();
    }

    private Properties mysqlProperties() {
        Properties properties = new Properties();
        properties.setProperty("useSSL", "false");
        properties.setProperty("allowPublicKeyRetrieval", "true");
        properties.setProperty("createDatabaseIfNotExist", "true");
        properties.setProperty("autoReconnect", "true");
        properties.setProperty("initialTimeout", "10");
        properties.setProperty("maxReconnects", "3");
        return properties;
    }
}