package ua.com.foxminded.krailo.university.domain;

import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.configuration.UniversityConfig;

@Component
public class Main {

    public static void main(String[] args) {
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UniversityConfig.class);
	ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	populator.addScript(new ClassPathResource("schema.sql"));
	populator.addScript(new ClassPathResource("data.sql"));
	DatabasePopulatorUtils.execute(populator, (DataSource) context.getBean("dataSource"));
	System.out.println("data populated");
	
	context.close();
    }
}