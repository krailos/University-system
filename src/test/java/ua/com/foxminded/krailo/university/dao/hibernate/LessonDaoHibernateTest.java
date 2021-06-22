package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Gender;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Year;

@Transactional
@SpringJUnitWebConfig(ConfigTest.class)
@Sql({ "classpath:schema.sql", "classpath:dataTest.sql" })
class LessonDaoHibernateTest {

    @Autowired
    private LessonDaoHibernate lessonDao;
    @Autowired
    private HibernateTemplate hibernateTemplate;

    @Test
    void givenNewLesson_whenCreate_thenCreated() {
	Lesson expected = getLesson();
	expected.setId(0);

	lessonDao.create(expected);

	assertEquals(expected, hibernateTemplate.get(Lesson.class, expected.getId()));
    }

    @Test
    void givenLessonId_whenGetById_thenLessonReturned() {
	Lesson expected = getLesson();
	
	Lesson actual = lessonDao.getById(1).get();

	assertEquals(expected, actual);
    }

    @Test
    void givenLessonsWithNewDateAndAudience_whenUpdate_thenUpdated() {
	Lesson expected = getLesson();
	expected.setDate(LocalDate.now());
	Audience audience = Audience.builder().id(3).number("3").capacity(120).description("description3").build();
	expected.setAudience(audience);

	lessonDao.update(expected);

	assertEquals(expected, hibernateTemplate.get(Lesson.class, expected.getId()));
    }

    @Test
    void givenLessons_whenGetByPage_thenLessonsReturned() {
	int pageNo = 0;
	int pageSize = 2;
	Pageable pageable = PageRequest.of(pageNo, pageSize);
	List<Lesson> expected = new ArrayList<>();
	expected.add(Lesson.builder()
		.id(1)
		.date(LocalDate.of(2021, 01, 01))
		.lessonTime(LessonTime.builder().id(1).orderNumber("first lesson").startTime(LocalTime.of(8, 30)).endTime(LocalTime.of(9, 15)).build())
		.subject(Subject.builder().id(1).name("subject 1").build())
		.audience(Audience.builder().id(1).number("1").capacity(300).description("description1").build())
		.teacher(Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
			.birthDate(LocalDate.of(2000, 01, 01)).phone("0670000001").address("address 1").email("email 1")
			.degree("0").gender(Gender.MALE).build()).groups(Arrays.asList(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build(),
				Group.builder().id(2).name("group 2").year(Year.builder().id(1).name("year 1").build()).build()))
		.build());
	expected.add(Lesson.builder()
		.id(2)
		.date(LocalDate.of(2021, 01, 01))
		.lessonTime(LessonTime.builder().id(2).orderNumber("second lesson").startTime(LocalTime.of(9, 30)).endTime(LocalTime.of(10, 15)).build())
		.subject(Subject.builder().id(2).name("subject 2").build())
		.audience(Audience.builder().id(2).number("2").capacity(120).description("description2").build())
		.teacher(Teacher.builder().id(2).firstName("first name 2").lastName("last name 2")
			.birthDate(LocalDate.of(2002, 02, 02)).phone("0670000002").address("address 2").email("email 2")
			.degree("0").gender(Gender.FEMALE).build())
		.groups(Arrays.asList(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build(),
				Group.builder().id(2).name("group 2").year(Year.builder().id(1).name("year 1").build()).build()))
		.build());
	
	List<Lesson> actual = lessonDao.getByPage(pageable);
	
	assertEquals(expected, actual);
    }
    
    @Test
    void givenLesson_whenDelete_thenDeleted() {
	Lesson lesson = getLesson();

	lessonDao.delete(lesson);

	assertNull(hibernateTemplate.get(Lesson.class, lesson.getId()));
    }

    @Test
    void whenGetAllLessons_thenAllLessonsReturned() {

	List<Lesson> actual = lessonDao.getAll();

	assertEquals(3, actual.size());
    }

    @Test
    void givenLesson_whengetByDate_thenFound() {
	List<Lesson> expected = new ArrayList<>();
	expected.add(Lesson.builder()
		.id(1)
		.date(LocalDate.of(2021, 01, 01))
		.lessonTime(LessonTime.builder().id(1).orderNumber("first lesson").startTime(LocalTime.of(8, 30)).endTime(LocalTime.of(9, 15)).build())
		.subject(Subject.builder().id(1).name("subject 1").build())
		.audience(Audience.builder().id(1).number("1").capacity(300).description("description1").build())
		.teacher(Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
			.birthDate(LocalDate.of(2000, 01, 01)).phone("0670000001").address("address 1").email("email 1")
			.degree("0").gender(Gender.MALE).build()).groups(Arrays.asList(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build(),
				Group.builder().id(2).name("group 2").year(Year.builder().id(1).name("year 1").build()).build()))
		.build());
	expected.add(Lesson.builder()
		.id(2)
		.date(LocalDate.of(2021, 01, 01))
		.lessonTime(LessonTime.builder().id(2).orderNumber("second lesson").startTime(LocalTime.of(9, 30)).endTime(LocalTime.of(10, 15)).build())
		.subject(Subject.builder().id(2).name("subject 2").build())
		.audience(Audience.builder().id(2).number("2").capacity(120).description("description2").build())
		.teacher(Teacher.builder().id(2).firstName("first name 2").lastName("last name 2")
			.birthDate(LocalDate.of(2002, 02, 02)).phone("0670000002").address("address 2").email("email 2")
			.degree("0").gender(Gender.FEMALE).build())
		.groups(Arrays.asList(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build(),
				Group.builder().id(2).name("group 2").year(Year.builder().id(1).name("year 1").build()).build()))
		.build());
	
	List<Lesson> actual = lessonDao.getByDate(LocalDate.of(2021, 01, 01));

	assertEquals(expected, actual);
    }

    @Test
    void givenTeacherAndDate_whenGetByTeacherAndDate_thenFound() {
	Teacher teacher = Teacher.builder().id(1).build();
	List<Lesson> expected = new ArrayList<>();
	expected.add(Lesson.builder()
		.id(1)
		.date(LocalDate.of(2021, 01, 01))
		.lessonTime(LessonTime.builder().id(1).orderNumber("first lesson").startTime(LocalTime.of(8, 30)).endTime(LocalTime.of(9, 15)).build())
		.subject(Subject.builder().id(1).name("subject 1").build())
		.audience(Audience.builder().id(1).number("1").capacity(300).description("description1").build())
		.teacher(Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
			.birthDate(LocalDate.of(2000, 01, 01)).phone("0670000001").address("address 1").email("email 1")
			.degree("0").gender(Gender.MALE).build())
		.groups(Arrays.asList(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build(),
				Group.builder().id(2).name("group 2").year(Year.builder().id(1).name("year 1").build()).build()))
		.build());

	List<Lesson> actual = lessonDao.getByTeacherAndDate(teacher, LocalDate.of(2021, 01, 01));

	assertEquals(expected, actual);
    }

    @Test
    void givenTeacherAndDate_whenFindByTeacherBetweenDates_thenFound() {
	Teacher teacher = Teacher.builder().id(1).build();
	List<Lesson> expected = new ArrayList<>();
	expected.add(Lesson.builder()
		.id(1)
		.date(LocalDate.of(2021, 01, 01))
		.lessonTime(LessonTime.builder().id(1).orderNumber("first lesson").startTime(LocalTime.of(8, 30)).endTime(LocalTime.of(9, 15)).build())
		.subject(Subject.builder().id(1).name("subject 1").build())
		.audience(Audience.builder().id(1).number("1").capacity(300).description("description1").build())
		.teacher(Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
			.birthDate(LocalDate.of(2000, 01, 01)).phone("0670000001").address("address 1").email("email 1")
			.degree("0").gender(Gender.MALE).build())
		.groups(Arrays.asList(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build(),
				Group.builder().id(2).name("group 2").year(Year.builder().id(1).name("year 1").build()).build()))
		.build());
	expected.add(Lesson.builder()
		.id(3)
		.date(LocalDate.of(2021, 01, 02))
		.lessonTime(LessonTime.builder().id(1).orderNumber("first lesson").startTime(LocalTime.of(8, 30)).endTime(LocalTime.of(9, 15)).build())
		.subject(Subject.builder().id(1).name("subject 1").build())
		.audience(Audience.builder().id(1).number("1").capacity(300).description("description1").build())
		.teacher(Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
			.birthDate(LocalDate.of(2000, 01, 01)).phone("0670000001").address("address 1").email("email 1")
			.degree("0").gender(Gender.MALE).build()).build());
	
	List<Lesson> actual = lessonDao.getByTeacherBetweenDates(teacher, LocalDate.of(2021, 01, 01), LocalDate.of(2021, 01, 05));

	assertEquals(expected, actual);
    }

    @Test
    void givenGroupAndDates_whenGetByGroupBetweenDates_thenFound() {
	Group group = Group.builder().id(1).build();
	List<Lesson> expected = new ArrayList<>();
	expected.add(Lesson.builder()
		.id(1)
		.date(LocalDate.of(2021, 01, 01))
		.lessonTime(LessonTime.builder().id(1).orderNumber("first lesson").startTime(LocalTime.of(8, 30)).endTime(LocalTime.of(9, 15)).build())
		.subject(Subject.builder().id(1).name("subject 1").build())
		.audience(Audience.builder().id(1).number("1").capacity(300).description("description1").build())
		.teacher(Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
			.birthDate(LocalDate.of(2000, 01, 01)).phone("0670000001").address("address 1").email("email 1")
			.degree("0").gender(Gender.MALE).build()).groups(Arrays.asList(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build(),
				Group.builder().id(2).name("group 2").year(Year.builder().id(1).name("year 1").build()).build()))
		.build());
	expected.add(Lesson.builder()
		.id(2)
		.date(LocalDate.of(2021, 01, 01))
		.lessonTime(LessonTime.builder().id(2).orderNumber("second lesson").startTime(LocalTime.of(9, 30)).endTime(LocalTime.of(10, 15)).build())
		.subject(Subject.builder().id(2).name("subject 2").build())
		.audience(Audience.builder().id(2).number("2").capacity(120).description("description2").build())
		.teacher(Teacher.builder().id(2).firstName("first name 2").lastName("last name 2")
			.birthDate(LocalDate.of(2002, 02, 02)).phone("0670000002").address("address 2").email("email 2")
			.degree("0").gender(Gender.FEMALE).build())
		.groups(Arrays.asList(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build(),
				Group.builder().id(2).name("group 2").year(Year.builder().id(1).name("year 1").build()).build()))
		.build());

	List<Lesson> actual = lessonDao.getByGroupBetweenDates(group, LocalDate.of(2021, 01, 01), LocalDate.of(2021, 01, 05));

	assertEquals(expected, actual);
    }

    @Test
    void givenGroupAndDate_whenGetdByGroupAndDate_thenFound() {
	Group group = Group.builder().id(1).build();
	List<Lesson> expected = new ArrayList<>();
	expected.add(Lesson.builder()
		.id(1)
		.date(LocalDate.of(2021, 01, 01))
		.lessonTime(LessonTime.builder().id(1).orderNumber("first lesson").startTime(LocalTime.of(8, 30)).endTime(LocalTime.of(9, 15)).build())
		.subject(Subject.builder().id(1).name("subject 1").build())
		.audience(Audience.builder().id(1).number("1").capacity(300).description("description1").build())
		.teacher(Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
			.birthDate(LocalDate.of(2000, 01, 01)).phone("0670000001").address("address 1").email("email 1")
			.degree("0").gender(Gender.MALE).build()).groups(Arrays.asList(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build(),
				Group.builder().id(2).name("group 2").year(Year.builder().id(1).name("year 1").build()).build()))
		.build());
	expected.add(Lesson.builder()
		.id(2)
		.date(LocalDate.of(2021, 01, 01))
		.lessonTime(LessonTime.builder().id(2).orderNumber("second lesson").startTime(LocalTime.of(9, 30)).endTime(LocalTime.of(10, 15)).build())
		.subject(Subject.builder().id(2).name("subject 2").build())
		.audience(Audience.builder().id(2).number("2").capacity(120).description("description2").build())
		.teacher(Teacher.builder().id(2).firstName("first name 2").lastName("last name 2")
			.birthDate(LocalDate.of(2002, 02, 02)).phone("0670000002").address("address 2").email("email 2")
			.degree("0").gender(Gender.FEMALE).build())
		.groups(Arrays.asList(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build(),
				Group.builder().id(2).name("group 2").year(Year.builder().id(1).name("year 1").build()).build()))
		.build());

	List<Lesson> actual= lessonDao.getByGroupAndDate(group, LocalDate.of(2021, 01, 01));

	assertEquals(expected, actual);
    }

    @Test
    void givenLesson_whenGetByDateBAndTeacherAndLessonTime_thenFound() {
	Lesson expected = Lesson.builder()
		.id(1)
		.date(LocalDate.of(2021, 01, 01))
		.lessonTime(LessonTime.builder().id(1).orderNumber("first lesson").startTime(LocalTime.of(8, 30)).endTime(LocalTime.of(9, 15)).build())
		.subject(Subject.builder().id(1).name("subject 1").build())
		.audience(Audience.builder().id(1).number("1").capacity(300).description("description1").build())
		.teacher(Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
			.birthDate(LocalDate.of(2000, 01, 01)).phone("0670000001").address("address 1").email("email 1")
			.degree("0").gender(Gender.MALE).build())
		.groups(Arrays.asList(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build(),
				Group.builder().id(2).name("group 2").year(Year.builder().id(1).name("year 1").build()).build()))
		.build();

	Lesson actual = lessonDao
		.getByDateAndTeacherAndLessonTime(expected.getDate(), expected.getTeacher(), expected.getLessonTime()).get();

	assertEquals(expected, actual);
    }

    @Test
    void givenLesson_whenGetByDateAndAudienceAndLessonTime_thenFound() {
	Lesson expected = Lesson.builder()
		.id(1)
		.date(LocalDate.of(2021, 01, 01))
		.lessonTime(LessonTime.builder().id(1).orderNumber("first lesson").startTime(LocalTime.of(8, 30)).endTime(LocalTime.of(9, 15)).build())
		.subject(Subject.builder().id(1).name("subject 1").build())
		.audience(Audience.builder().id(1).number("1").capacity(300).description("description1").build())
		.teacher(Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
			.birthDate(LocalDate.of(2000, 01, 01)).phone("0670000001").address("address 1").email("email 1")
			.degree("0").gender(Gender.MALE).build())
		.groups(Arrays.asList(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build(),
				Group.builder().id(2).name("group 2").year(Year.builder().id(1).name("year 1").build()).build()))
		.build();
	
	Lesson actual = lessonDao
		.getByDateAndAudienceAndLessonTime(expected.getDate(), expected.getAudience(), expected.getLessonTime())
		.get();

	assertEquals(expected, actual);
    }

    @Test
    void givenDateLessonTimeAndGroup_whenGetByDateAndLessonTimeAndGroup_thenFound() {
	Lesson expected = Lesson.builder()
		.id(1)
		.date(LocalDate.of(2021, 01, 01))
		.lessonTime(LessonTime.builder().id(1).orderNumber("first lesson").startTime(LocalTime.of(8, 30)).endTime(LocalTime.of(9, 15)).build())
		.subject(Subject.builder().id(1).name("subject 1").build())
		.audience(Audience.builder().id(1).number("1").capacity(300).description("description1").build())
		.teacher(Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
			.birthDate(LocalDate.of(2000, 01, 01)).phone("0670000001").address("address 1").email("email 1")
			.degree("0").gender(Gender.MALE).build())
		.groups(Arrays.asList(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build(),
				Group.builder().id(2).name("group 2").year(Year.builder().id(1).name("year 1").build()).build()))
		.build();
	
	Lesson actual = lessonDao
		.getByDateAndLessonTimeAndGroup(expected.getDate(), expected.getLessonTime(), expected.getGroups().get(0))
		.get();

	assertEquals(expected, actual);
    }

    @Test
    void givenLessons_whenCount_thenCounted() {

	int actual = lessonDao.count();

	assertEquals(3, actual);
    }

    private Lesson getLesson() {
	return Lesson.builder()
		.id(1)
		.date(LocalDate.of(2021, 01, 01))
		.lessonTime(LessonTime.builder().id(1).orderNumber("first lesson").startTime(LocalTime.of(8, 30)).endTime(LocalTime.of(9, 15)).build())
		.subject(Subject.builder().id(1).name("subject 1").build())
		.audience(Audience.builder().id(1).number("1").capacity(300).description("description1").build())
		.teacher(Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
			.birthDate(LocalDate.of(2000, 01, 01)).phone("0670000001").address("address 1").email("email 1")
			.degree("0").gender(Gender.MALE).build()).groups(Arrays.asList(Group.builder().id(1).name("group 1").year(Year.builder().id(1).name("year 1").build()).build(),
				Group.builder().id(2).name("group 2").year(Year.builder().id(1).name("year 1").build()).build()))
		.build();
    }

}
