package ua.com.foxminded.krailo.university.domain;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ua.com.foxminded.krailo.university.dao.BuildingDao;
import ua.com.foxminded.krailo.university.dao.ScriptRunnerDao;

public class Main {

    public static void main(String[] args) {

	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	ScriptRunnerDao scriptRunnerDao = context.getBean("scriptRunnerDao", ScriptRunnerDao.class); 
	scriptRunnerDao.runScript("schema.sql");
	BuildingDao buildingDao = context.getBean(BuildingDao.class);
	buildingDao.findAll().forEach(b -> System.out.println(b.getAudiences()));
	
    } 
}