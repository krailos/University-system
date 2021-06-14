package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.Holiday;

@Transactional
@SpringJUnitWebConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class HolidayDaoHibernateTet {

    @Autowired
    private HolidyDaoHibernate holidyDaoHibernate;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void test() {

	List<Holiday> holidays = holidyDaoHibernate.getAll();
	for (Holiday holiday : holidays) {
	    System.out.println(holiday);
	}
	assertEquals(2, holidays.size());
    }

}
