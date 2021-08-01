package ua.com.foxminded.krailo.university;

import org.hibernate.SessionFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.web.WebAppConfiguration;

import ua.com.foxminded.krailo.university.config.UniversityConfig;

@TestConfiguration
@Import(UniversityConfig.class)
@PropertySource("classpath:application.yml")
public class ConfigTest {

//    @Bean
//    public DataSource dataSource() {
//	DriverManagerDataSource dataSource = new DriverManagerDataSource();
//	dataSource.setDriverClassName("org.h2.Driver");
//	dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
//	dataSource.setUsername("sa");
//	dataSource.setPassword("");
//	return dataSource;
//    }

    @Bean
    public HibernateTemplate hibernateTemplate(SessionFactory sessionFactory) {
	return new HibernateTemplate(sessionFactory);
    }

}
