package ua.com.foxminded.krailo.university.testConfig;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import ua.com.foxminded.krailo.university.dao.BuildingDao;

@Configuration
@ComponentScan("ua.com.foxminded.krailo.university")
public class TestConfig {
 
    @Bean
    public BuildingDao getBuildingDao() {
	return new BuildingDao();
    }
    
    @Bean
    public DataSource getDataSource() {
	return new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
		.setScriptEncoding("UTF-8").ignoreFailedDrops(true).addScript("schemaTest.sql").build();
    }
    
    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
	JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	return jdbcTemplate;
    }
        
}
