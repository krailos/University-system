package ua.com.foxminded.krailo.university.testConfig;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import ua.com.foxminded.krailo.university.configuration.UniversityConfig;

@Configuration
@Import(UniversityConfig.class)
public class ConfigTest {
 
    @Bean
    public DataSource dataSource() {
	DriverManagerDataSource dataSource = new DriverManagerDataSource();
	dataSource.setDriverClassName("org.h2.Driver");
	dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=PostgreSQL");
	dataSource.setUsername("sa");
	dataSource.setPassword("");
	return dataSource;
	
	//return new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.H2).build();
    }
              
}
