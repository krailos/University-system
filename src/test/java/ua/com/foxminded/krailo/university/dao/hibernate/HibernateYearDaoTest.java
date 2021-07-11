package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.ConfigTest;
import ua.com.foxminded.krailo.university.dao.YearDao;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Year;

@Transactional
@SpringJUnitWebConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class HibernateYearDaoTest {

    @Autowired
    private YearDao yearDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenNewYear_whenCreate_thenCreated() {
	Year expected = Year.builder().id(1).name("new year 1").build();
	expected.setId(0);

	yearDao.create(expected);

	assertEquals(expected, hibernateTemplate.get(Year.class, expected.getId()));
    }

    @Test
    void givenNewYearWithSubjects_whenCreate_thenNewRowsInYearsSubjectsCreated() {
	Year expected = Year.builder().id(1).name("new year 1").build();
	List<Subject> subjects = new ArrayList<>(Arrays.asList(Subject.builder().id(3).name("new subject").build(),
		Subject.builder().id(4).name("new subject").build()));
	expected.setSubjects(subjects);

	yearDao.create(expected);

	assertEquals(subjects, expected.getSubjects());
    }

    @Test
    void givenNewFieldsOfYear_whenUpdate_tnenUpdated() {
	Year expected = Year.builder().id(1).name("new name").build();
	List<Subject> subjects = new ArrayList<>(Arrays.asList(Subject.builder().id(1).name("new subject").build(),
		Subject.builder().id(2).name("new subject").build()));
	expected.setSubjects(subjects);

	yearDao.update(expected);

	assertEquals(expected, hibernateTemplate.get(Year.class, expected.getId()));
    }

    @Test
    void givenId_whenGetById_thenGot() {
	Year expected = Year.builder().id(1).name("year 1").build();

	Year actual = yearDao.getById(1).get();

	assertEquals(expected, actual);
    }

    @Test
    void givenYears_whenGetAll_thenFound() {
	List<Year> expected = new ArrayList<>();
	expected.add(Year.builder().id(1).name("year 1").build());
	expected.add(Year.builder().id(2).name("year 2").build());
	expected.add(Year.builder().id(3).name("year 3").build());

	List<Year> actual = yearDao.getAll();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDelete_thenDeleted() {
	Year year = Year.builder().id(1).name("new year 1").build();

	yearDao.delete(year);

	assertNull(hibernateTemplate.get(Year.class, year.getId()));
    }

}
