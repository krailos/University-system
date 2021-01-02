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
	System.out.println(holidayDao.findAll());
	
//	Holiday holiday2 = new Holiday("Eater", LocalDate.of(2021, 05, 02));
//	holidayDao.addHoliday(holiday2);
	
//	holidayDao.updateName("Christmas", 3);
//	holidayDao.updateDate(LocalDate.of(2021, 01, 07), 3);

//	holidayDao.deleteById(10);
//	System.out.println(holidayDao.findAll());

	
	
	
    }

}