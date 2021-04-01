package ua.com.foxminded.krailo.university.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import ua.com.foxminded.krailo.university.configuration.UniversityConfig;
import ua.com.foxminded.krailo.university.dao.YearDao;
import ua.com.foxminded.krailo.university.model.Speciality;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Year;

@Component
public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

	LOGGER.info("Entering application.");
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UniversityConfig.class);
	ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	populator.addScript(new ClassPathResource("schema.sql"));
	populator.addScript(new ClassPathResource("data.sql"));
	DatabasePopulatorUtils.execute(populator, (DataSource) context.getBean("dataSource"));
	LOGGER.info("Data populated");
	
	
	YearDao yearDao = context.getBean(YearDao.class);
	System.out.println(yearDao.findById(1));
	
	try {
	Year year = Year.builder().id(1).name("new name")
		.speciality(Speciality.builder().id(1).name("new name").build()).build();
	List<Subject> subjects = new ArrayList<>(Arrays.asList(Subject.builder().id(1).name("new subject").build(),
		Subject.builder().id(2).name("new subject").build()));
	year.setSubjects(subjects);

	yearDao.update(year);
	
	System.out.println(yearDao.findById(1));
	} catch (Exception e) {
	    System.out.println(yearDao.findById(1));
	  e.printStackTrace();
	}

	context.close();
    }
}