package ua.com.foxminded.krailo.university.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("ua.com.foxminded.krailo.university")
@PropertySource("classpath:config.properties")
public class ConfigUniversityOffice {

    private Environment env;

    public ConfigUniversityOffice(Environment env) {
	this.env = env;
    }

    @Bean
    public DataSource dataSource() {
	DriverManagerDataSource dataSource = new DriverManagerDataSource();
	dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
	dataSource.setUrl(env.getProperty("jdbc.url"));
	dataSource.setUsername(env.getProperty("jdbc.username"));
	dataSource.setPassword(env.getProperty("jdbc.password"));
	return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	return new JdbcTemplate(dataSource);

    }

}
