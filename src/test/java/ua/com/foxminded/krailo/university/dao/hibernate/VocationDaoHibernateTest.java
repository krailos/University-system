package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.Year;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.dao.interf.VocationDaoInt;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Vocation;
import ua.com.foxminded.krailo.university.model.VocationKind;

@Transactional
@SpringJUnitWebConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class VocationDaoHibernateTest {

    @Autowired
    private VocationDaoInt vocationDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenNewVocation_whenCreate_thenCreated() {
	Vocation vocation = getVocation();
	vocation.setId(0);

	vocationDao.create(vocation);

	assertEquals(vocation.getId(), hibernateTemplate.get(Vocation.class, 3).getId());
    }

    @Test
    void givenNewFieldsOfVocation_whenUpdate_thenUpdated() {
	Vocation vocation = getVocation();
	vocation.setKind(VocationKind.PREFERENTIAL);

	vocationDao.update(vocation);

	assertEquals(vocation.getKind(), hibernateTemplate.get(Vocation.class, 1).getKind());
    }

    @Test
    void givenId_whenGetById_thenGot() {

	Vocation actual = vocationDao.getById(1).get();

	assertEquals(1, actual.getId());
    }

    @Test
    void givenVocations_whenGrtAll_thenGot() {

	int actual = vocationDao.getAll().size();

	assertEquals(2, actual);
    }

    @Test
    void givenTeacher_whenGetByTeacher_thenGot() {
	Vocation vocation = getVocation();

	int actual = vocationDao.getByTeacher(vocation.getTeacher()).size();

	assertEquals(1, actual);
    }

    @Test
    void givenId_whenDelete_thenDeleted() throws Exception {
	Vocation vocation = getVocation();

	vocationDao.delete(vocation);

	assertEquals(null, hibernateTemplate.get(Vocation.class, 1));
    }

    @Test
    void givenId_whenGetByTeacherAndDate_thenGot() {
	Vocation vocation = getVocation();

	Vocation actual = vocationDao.getByTeacherAndDate(vocation.getTeacher(), LocalDate.of(2021, 02, 07)).get();

	assertEquals(1, actual.getTeacher().getId());
	assertTrue(LocalDate.of(2021, 02, 07).isAfter(actual.getStart().minusDays(1))
		&& LocalDate.of(2021, 02, 07).isBefore(actual.getEnd().plusDays(1)));
    }

    @Test
    void givenVocation_whenGetByTeacherAndYear_thenGot() {
	Vocation vocation = getVocation();

	int actual = vocationDao.getByTeacherAndYear(vocation.getTeacher(), Year.from(vocation.getStart())).size();

	assertEquals(1, actual);
    }

    private Vocation getVocation() {
	return Vocation.builder().id(1).kind(VocationKind.GENERAL).applyingDate(LocalDate.of(2021, 01, 01))
		.startDate(LocalDate.of(2021, 02, 02)).endDate(LocalDate.of(2021, 02, 10))
		.teacher(Teacher.builder().id(1).build()).build();
    }

}
