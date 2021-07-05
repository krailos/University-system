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
@PropertySource("classpath:application.yml")
@EnableTransactionManagement(proxyTargetClass = true)
public class UniversityConfig {

//    @Value("hibernate.show_sql")
//    private String showSql;
//    @Value("hibernate.format_sql")
//    private String formatSql;
//    @Value("${hibernateHbm2ddl}")
//    private String hibernateHbm2ddl;
//    @Value("${hibernate.dialect}")
//    private String hibernateDialect;
//    @Value("${current_session_context_class}")
//    private String currentSessionContextClass;
//    @Value("${hibernate.enable_lazy_load_no_trans}")
//    private String hibernateLazyLoad;

//    @Value("${jdbc.driver}")
//    private String driver;
//    @Value("${jdbc.url}")
//    private String url;
//    @Value("${jdbc.username}")
//    private String user;
//    @Value("${jdbc.password}")
//    private String password;

//    @Bean
//    public DataSource dataSource() throws NamingException {
//	DriverManagerDataSource dataSource = new DriverManagerDataSource();
//	dataSource.setDriverClassName(driver);
//	dataSource.setUrl(url);
//	dataSource.setUsername(user);
//	dataSource.setPassword(password);
//	return dataSource;
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//	return new JdbcTemplate(dataSource);
//    }

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

//    private final Properties hibernateProperties() {
//	Properties hibernateProperties = new Properties();
//	hibernateProperties.setProperty("show_sql", showSql);
//	hibernateProperties.setProperty("format_sql", formatSql);
//	hibernateProperties.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddl);
//	hibernateProperties.setProperty("hibernate.dialect", hibernateDialect);
//	hibernateProperties.setProperty("current_session_context_class", currentSessionContextClass);
//	hibernateProperties.setProperty("hibernate.enable_lazy_load_no_trans", hibernateLazyLoad);
//	return hibernateProperties;
//    }

}
