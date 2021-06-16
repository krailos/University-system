package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.dao.YearDao;
import ua.com.foxminded.krailo.university.dao.interf.YearDaoInt;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Year;

@Transactional
@SpringJUnitWebConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class YearDaoHibernateTest {

    @Autowired
    private YearDaoInt yearDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenNewYear_whenCreate_thenCreated() {
	Year year = Year.builder().id(1).name("new year 1").build();
	year.setId(0);

	yearDao.create(year);

	assertEquals(year, year);
    }

    @Test
    void givenNewYearWithSubjects_whenCreate_thenNewRowsInYearsSubjectsCreated() {
	Year year = Year.builder().id(1).name("new year 1").build();
	List<Subject> subjects = new ArrayList<>(Arrays.asList(Subject.builder().id(3).name("new subject").build(),
		Subject.builder().id(4).name("new subject").build()));
	year.setSubjects(subjects);

	yearDao.create(year);

	assertEquals(2, year.getSubjects().size());
    }

    @Test
    void givenNewFieldsOfYear_whenUpdate_tnenUpdated() {
	Year year = Year.builder().id(1).name("new name").build();
	List<Subject> subjects = new ArrayList<>(Arrays.asList(Subject.builder().id(1).name("new subject").build(),
		Subject.builder().id(2).name("new subject").build()));
	year.setSubjects(subjects);

	yearDao.update(year);

	assertEquals(year.getName(), hibernateTemplate.get(Year.class, 1).getName());
	assertEquals(2, hibernateTemplate.get(Year.class, 1).getSubjects().size());
    }

    @Test
    void givenId_whenGetById_thenGot() {

	Year actual = yearDao.getById(1).get();

	assertEquals(1, actual.getId());
    }

    @Test
    void givenYears_whenGetAll_thenFound() {
	
	int actual = yearDao.getAll().size();

	assertEquals(3, actual);
    }

    @Test
    void givenId_whenDelete_thenDeleted() {
	Year year = Year.builder().id(1).name("new year 1").build();
	
	yearDao.delete(year);

	assertEquals(null, hibernateTemplate.get(Year.class, 1));
    }

}
