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
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.ucl.newton.common.file.SystemUtils;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.nio.file.Path;
import java.util.Properties;

/**
 * Instances of this class configure the systems data persistence
 * infrastructure. Specifically this class starts an in memory database and
 * populates it with mock data. This database is then provided to the Hibernate
 * framework, through which queries will be made and data accessed.
 *
 * @author Blair Butterworth
 */
@Configuration
@Profile("development")
@EnableTransactionManagement
@ComponentScan(basePackages = {"org.ucl.newton.service", "org.ucl.newton.framework"})
@SuppressWarnings("unused")
public class DeveloperPersistenceConfiguration
{
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[]{"org.ucl.newton.framework"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        builder.setType(EmbeddedDatabaseType.H2);
        builder.continueOnError(true);
        return builder.build();
    }

    @Bean
    @Inject
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory);
        return transactionManager;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer() {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("/sql/schema.sql"));
        resourceDatabasePopulator.addScript(new ClassPathResource("/sql/data.sql"));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource());
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        properties.put("hibernate.show_sql", "true");
//        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.search.default.directory_provider", "filesystem");
        properties.put("hibernate.search.default.indexBase", getIndexPath());
        return properties;
    }

    private String getIndexPath() {
        Path indexPath = SystemUtils.newTempDirectory("developer/index");
        indexPath.toFile().delete();
        return indexPath.toString();
    }
}
