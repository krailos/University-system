package ua.com.foxminded.krailo.university.testConfig;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import ua.com.foxminded.krailo.university.configuration.UniversityConfig;

@Configuration
@ComponentScan("ua.com.foxminded.krailo.university")
@Import({UniversityConfig.class})
public class ConfigTest {
 
    @Bean
    public DataSource getDataSource() {
	return new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.H2).build();
    }
              
}
