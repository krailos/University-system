package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.Speciality;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Year;

@SpringJUnitConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class YearDaoTest {

    @Autowired
    private YearDao yearDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void givenNewYear_whenCreate_thenCreated() {
	Year year = new Year.YearBuilder().withName("new name")
		.withSpeciality(new Speciality.SpecialityBuilder().withId(1).withName("new name").built()).built();

	yearDao.create(year);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "years");
	assertEquals(4, actual);
    }

    @Test
    void givenNewYearWithSubjects_whenCreate_thenNewRowsInYearsSubjectsCreated() {
	Year year = new Year.YearBuilder().withName("new name")
		.withSpeciality(new Speciality.SpecialityBuilder().withId(1).withName("new name").built()).built();
	List<Subject> subjects = new ArrayList<>(
		Arrays.asList(new Subject.SubjectBuilder().withId(3).withName("new subject").built(),
			new Subject.SubjectBuilder().withId(4).withName("new subject").built()));
	year.setSubjects(subjects);

	yearDao.create(year);

	int expected = 2;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "years_subjects", "year_id = 2");
	assertEquals(expected, actual);
    }

    @Test
    void givenNewFieldsOfYear_whenUpdate_tnenUpdated() {
	Year year = new Year.YearBuilder().withId(1).withName("new name")
		.withSpeciality(new Speciality.SpecialityBuilder().withId(1).withName("new name").built()).built();

	yearDao.update(year);

	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "years", "name = 'new name' AND id = 1");
	assertEquals(1, actual);
    }

    @Test
    void givenNewSubjectsOfYear_whenUpdate_thenNewRowsInYearsSubjectsUpdated() {
	Year year = new Year.YearBuilder().withId(1).withName("new name")
		.withSpeciality(new Speciality.SpecialityBuilder().withId(1).withName("new name").built()).built();
	List<Subject> subjects = new ArrayList<>(
		Arrays.asList(new Subject.SubjectBuilder().withId(1).withName("new subject").built(),
			new Subject.SubjectBuilder().withId(2).withName("new subject").built()));
	year.setSubjects(subjects);

	yearDao.update(year);

	int expected = 2;
	int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "years_subjects", "year_id = 1");
	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenFindById_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "years", "id =1");

	int actual = yearDao.findById(1).getId();

	assertEquals(expected, actual);
    }

    @Test
    void givenYears_whenFindAll_thenFound() {
	int expected = JdbcTestUtils.countRowsInTable(jdbcTemplate, "years");

	int actual = yearDao.findAll().size();

	assertEquals(expected, actual);
    }

    @Test
    void givenSpecialityId_whenFindBySpecialityId_thenFound() {
	int expected = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "years", "speciality_id = 1");

	int actual = yearDao.findBySpecialityId(1).size();

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDeleteById_thenDeleted() {

	yearDao.deleteById(1);

	int actual = JdbcTestUtils.countRowsInTable(jdbcTemplate, "years");
	assertEquals(2, actual);
    }

}
