package ua.com.foxminded.krailo.university.config;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("ua.com.foxminded.krailo.university")
@PropertySource({ "classpath:config.properties", "classpath:jndi.properties" })
@EnableTransactionManagement
public class UniversityConfig {

    @Value("${jdbc.jndi.name}")
    private String jdbcJndiName;

    @Bean
    public DataSource dataSource() throws NamingException {
	return (DataSource) new JndiTemplate().lookup(jdbcJndiName);

    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
	return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
	DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
	dataSourceTransactionManager.setDataSource(dataSource);
	return dataSourceTransactionManager;
    }

}
