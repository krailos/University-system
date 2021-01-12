package ua.com.foxminded.krailo.university.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import ua.com.foxminded.krailo.university.util.PropertyLoader;

@Configuration
@ComponentScan("ua.com.foxminded.krailo.university")
public class UniversityOfficeConfig {

    @Bean
    public DataSource getDataSource() {
	DriverManagerDataSource dataSource = new DriverManagerDataSource();
	dataSource.setDriverClassName(PropertyLoader.getProperties("jdbc.driver"));
	dataSource.setUrl(PropertyLoader.getProperties("jdbc.url"));
	dataSource.setUsername(PropertyLoader.getProperties("jdbc.username"));
	dataSource.setPassword(PropertyLoader.getProperties("jdbc.password"));
	return dataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	return jdbcTemplate;
    }

}
