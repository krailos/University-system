package ua.com.foxminded.krailo.university.domain;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.model.Holiday;


public class Main {

    public static void main(String[] args) {

	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//	ScriptRunnerDao scriptRunnerDao = context.getBean("scriptRunnerDao", ScriptRunnerDao.class); 
//	scriptRunnerDao.runScript("schema.sql");
	
	HolidayDao holidayDao  = context.getBean(HolidayDao.class);
	Holiday holiday = holidayDao.findById(1);
	System.out.println(holiday);
	
    }

}