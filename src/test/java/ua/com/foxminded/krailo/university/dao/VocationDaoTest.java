package ua.com.foxminded.krailo.university.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.ConfigTest;
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Vocation;
import ua.com.foxminded.krailo.university.model.VocationKind;

@Transactional
@DataJpaTest
@ContextConfiguration(classes = ConfigTest.class)
class VocationDaoTest {

    @Autowired
    private VocationDao vocationDao;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    void givenNewVocation_whenCreate_thenCreated() {
	Vocation expected = getVocation();
	expected.setId(0);

	vocationDao.save(expected);

	assertEquals(expected, entityManager.find(Vocation.class, expected.getId()));
    }

    @Test
    void givenNewFieldsOfVocation_whenUpdate_thenUpdated() {
	Vocation expected = getVocation();
	expected.setKind(VocationKind.PREFERENTIAL);

	vocationDao.save(expected);

	assertEquals(expected, entityManager.find(Vocation.class, expected.getId()));
    }

    @Test
    void givenId_whenGetById_thenGot() {
	Vocation expected = getVocation();

	Vocation actual = vocationDao.findById(1).get();

	assertEquals(expected, actual);
    }

    @Test
    void givenVocations_whenGrtAll_thenGot() {
	Teacher teacher1 = Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
		.birthDate(LocalDate.of(2000, 01, 01)).address("address 1").phone("0670000001").email("email 1")
		.degree("0").gender(Gender.MALE).build();
	Teacher teacher2 = Teacher.builder().id(2).firstName("first name 2").lastName("last name 2")
		.birthDate(LocalDate.of(2002, 02, 02)).address("address 2").phone("0670000002").email("email 2")
		.degree("0").gender(Gender.FEMALE).build();
	List<Vocation> expected = new ArrayList<Vocation>();
	expected.add(Vocation.builder().id(1).kind(VocationKind.GENERAL).applyingDate(LocalDate.of(2021, 01, 01))
		.startDate(LocalDate.of(2021, 02, 02)).endDate(LocalDate.of(2021, 02, 10)).teacher(teacher1).build());
	expected.add(Vocation.builder().id(2).kind(VocationKind.GENERAL).applyingDate(LocalDate.of(2022, 01, 01))
		.startDate(LocalDate.of(2022, 02, 02)).endDate(LocalDate.of(2022, 02, 10)).teacher(teacher2).build());

	List<Vocation> actual = vocationDao.findAll();

	assertEquals(expected, actual);
    }

    @Test
    void givenTeacher_whenGetByTeacher_thenGot() {
	Teacher teacher = Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
		.birthDate(LocalDate.of(2000, 01, 01)).address("address 1").phone("0670000001").email("email 1")
		.degree("0").gender(Gender.MALE).build();
	Vocation vocation = getVocation();
	vocation.setTeacher(teacher);
	List<Vocation> expected = new ArrayList<Vocation>();
	expected.add(vocation);

	List<Vocation> actual = vocationDao.findByTeacher(teacher);

	assertEquals(expected, actual);
    }

    @Test
    void givenId_whenDelete_thenDeleted() throws Exception {
	Vocation vocation = getVocation();

	vocationDao.delete(vocation);

	assertNull(entityManager.find(Vocation.class, vocation.getId()));
    }

    @Test
    void givenId_whenGetByTeacherAndDate_thenGot() {
	Teacher teacher = Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
		.birthDate(LocalDate.of(2000, 01, 01)).address("address 1").phone("0670000001").email("email 1")
		.degree("0").gender(Gender.MALE).build();
	Vocation expected = getVocation();
	expected.setTeacher(teacher);

	Vocation actual = vocationDao
		.findByTeacherAndDate(expected.getTeacher().getId(), LocalDate.of(2021, 02, 07)).get();

	assertEquals(expected, actual);
    }

    @Test
    void givenVocation_whenGetByTeacherAndYear_thenGot() {
	Teacher teacher = Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
		.birthDate(LocalDate.of(2000, 01, 01)).address("address 1").phone("0670000001").email("email 1")
		.degree("0").gender(Gender.MALE).build();
	Vocation vocation = getVocation();
	vocation.setTeacher(teacher);
	List<Vocation> expected = new ArrayList<Vocation>();
	expected.add(vocation);

	List<Vocation> actual = vocationDao.findByTeacherAndApplyingDateBetween(teacher,
		LocalDate.of(vocation.getStart().getYear(), 1, 1), LocalDate.of(vocation.getStart().getYear(), 12, 31));

	assertEquals(expected, actual);
    }

    private Vocation getVocation() {
	Teacher teacher = Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
		.birthDate(LocalDate.of(2000, 01, 01)).address("address 1").phone("0670000001").email("email 1")
		.degree("0").gender(Gender.MALE).build();

	return Vocation.builder().id(1).kind(VocationKind.GENERAL).applyingDate(LocalDate.of(2021, 01, 01))
		.startDate(LocalDate.of(2021, 02, 02)).endDate(LocalDate.of(2021, 02, 10)).teacher(teacher).build();
    }

}
