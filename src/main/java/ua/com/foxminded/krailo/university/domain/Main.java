package ua.com.foxminded.krailo.university.domain;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.com.foxminded.krailo.university.configuration.UniversityOfficeConfig;
import ua.com.foxminded.krailo.university.dao.ScriptRunnerDao;

public class Main {

    public static void main(String[] args) {
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UniversityOfficeConfig.class);
	ScriptRunnerDao scriptRunnerDao = context.getBean(ScriptRunnerDao.class); 
	scriptRunnerDao.runScript("schema.sql");
	scriptRunnerDao.runScript("data.sql");	
	context.close();
    } 
}