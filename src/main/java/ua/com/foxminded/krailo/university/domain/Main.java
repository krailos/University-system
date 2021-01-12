package ua.com.foxminded.krailo.university.domain;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.com.foxminded.krailo.university.configuration.UniversityOfficeConfig;
import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.dao.BuildingDao;
import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.dao.ScriptRunnerDao;
import ua.com.foxminded.krailo.university.model.Audience;

public class Main {

    public static void main(String[] args) {
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UniversityOfficeConfig.class);
	ScriptRunnerDao scriptRunnerDao = context.getBean(ScriptRunnerDao.class); 
//	scriptRunnerDao.runScript("schema.sql");
//	scriptRunnerDao.runScript("data.sql");
	HolidayDao holidayDao = context.getBean(HolidayDao.class);
//	Holiday holiday = new Holiday("Christmas", LocalDate.of(2021, 01, 7));
//	holidayDao.createHoliday(holiday);
	System.out.println(holidayDao.findAll());
	BuildingDao buildingDao = context.getBean(BuildingDao.class);
	System.out.println(buildingDao.findAll());
	
	System.out.println(buildingDao.findById(2));
	AudienceDao audienceDao = context.getBean(AudienceDao.class);
	System.out.println(audienceDao.findAudiencesByBuildingId(2));
	System.out.println(buildingDao.findById(2).getAudiences());
	System.out.println(audienceDao.findAll());
	
	Audience audience = audienceDao.findById(1);
	System.out.println(audience);
	
	
	context.close();
    } 
}