package ua.com.foxminded.krailo.university.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("ua.com.foxminded.krailo.university")
@EnableTransactionManagement(proxyTargetClass = true)
public class UniversityConfig {

    @Bean	
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
	LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	sessionFactory.setDataSource(dataSource);
	sessionFactory.setPackagesToScan(new String[] { "ua.com.foxminded.krailo.university" });
	return sessionFactory;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager(LocalSessionFactoryBean localSessionFactoryBean) {
	HibernateTransactionManager transactionManager = new HibernateTransactionManager();
	transactionManager.setSessionFactory(localSessionFactoryBean.getObject());
	return transactionManager;
    }


}
