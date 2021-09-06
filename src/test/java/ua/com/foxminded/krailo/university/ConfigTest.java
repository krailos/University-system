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

    @Bean
    public HibernateTemplate hibernateTemplate(SessionFactory sessionFactory) {
	return new HibernateTemplate(sessionFactory);
    }

}
