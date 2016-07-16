package com.spring.conf;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
class JPAConfiguration {
   /*
      MySQL no openshift
      Root User: adminrlWgtSj
      Root Password: 33zC1UbEU3Ra
      Database Name: spring
      Connection URL: mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT/

   private static final String URL = "mysql://$OPENSHIFT_MYSQL_DB_HOST:$OPENSHIFT_MYSQL_DB_PORT";
   private static final String DB_USER = "adminrlWgtSj";
   private static final String DB_PASS = "33zC1UbEU3Ra";
   */

   private static final String DRIVER = "com.mysql.jdbc.Driver";
   private static final String URL = "jdbc:mysql://localhost/spring_app";
   private static final String DB_USER = "root";
   private static final String DB_PASS = "";

   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      em.setDataSource(dataSource);
      em.setPackagesToScan(new String[] { "com.spring.models" });

      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      em.setJpaVendorAdapter(vendorAdapter);
      em.setJpaProperties(additionalProperties());

      return em;
   }

   @Bean
   public DataSource dataSource(Environment environment) {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName(DRIVER);
      dataSource.setUrl(URL);
      dataSource.setUsername(DB_USER);
      dataSource.setPassword(DB_PASS);
      return dataSource;
   }

   @Bean
   public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(emf);
      return transactionManager;
   }

   @Bean
   public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
      return new PersistenceExceptionTranslationPostProcessor();
   }

   private Properties additionalProperties() {
      Properties properties = new Properties();
      properties.setProperty("hibernate.hbm2ddl.auto", "update");
      properties.setProperty("hibernate.show_sql", "true");
      return properties;
   }
}
