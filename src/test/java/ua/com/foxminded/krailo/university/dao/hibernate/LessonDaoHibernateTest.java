package ua.com.foxminded.krailo.university.dao.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.LocalTime;
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
import ua.com.foxminded.krailo.university.model.Student;
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
	Lesson lesson = getLesson();
	lesson.setId(0);

	lessonDao.create(lesson);
	
	assertEquals(lesson, hibernateTemplate.get(Lesson.class, 7));
    }

    @Test
    void givenLessonId_whenGetById_thenLessonReturned() {
	
	Lesson actual = lessonDao.getById(1).get();

	assertEquals(1, actual.getId());
    }

    @Test
    void givenLessonsWithNewDateAndAudience_whenUpdate_thenUpdated() {
	Lesson lesson = getLesson();
	lesson.setDate(LocalDate.now());
	Audience audience = Audience.builder().id(3).number("3").capacity(120).description("description3").build();
	lesson.setAudience(audience);

	lessonDao.update(lesson);

	assertEquals(lesson.getDate(), hibernateTemplate.get(Lesson.class, 1).getDate());
	assertEquals(lesson.getAudience(), hibernateTemplate.get(Lesson.class, 1).getAudience());
    }

    @Test
    void givenLesson_whenDelete_thenDeleted() {
	Lesson lesson = getLesson();

	lessonDao.delete(lesson);

	assertNull(hibernateTemplate.get(Lesson.class, 1));
    }

    @Test
    void givenLessons_whenGetByPage_thenLessonsReturned() {
	int pageNo = 1;
	int pageSize = 3;
	Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

	assertEquals(3, lessonDao.getByPage(pageable).size());
    }

    @Test
    void whenGetAllLessons_thenAllLessonsReturned() {

	List<Lesson> actual = lessonDao.getAll();

	assertEquals(6, actual.size());
    }

    @Test
    void givenLesson_whengetByDate_thenFound() {
	Lesson lesson = getLesson();

	int actual = lessonDao.getByDate(lesson.getDate()).size();

	assertEquals(2, actual);
    }

    @Test
    void givenTeacherAndDate_whenGetByTeacherAndDate_thenFound() {
	Teacher teacher = Teacher.builder().id(1).build();

	int actual = lessonDao.getByTeacherAndDate(teacher, LocalDate.of(2021, 01, 01)).size();

	assertEquals(1, actual);
    }

    @Test
    void givenTeacherAndDate_whenFindByTeacherBetweenDates_thenFound() {
	Teacher teacher = Teacher.builder().id(1).build();

	int actual = lessonDao.getByTeacherBetweenDates(teacher, LocalDate.of(2021, 01, 01), LocalDate.of(2021, 01, 05))
		.size();

	assertEquals(5, actual);
    }

    @Test
    void givenStudentAndDates_whenGetByStudentBetweenDates_thenFound() {
	Student student = Student.builder().id(1).build();

	int actual = lessonDao.getByStudentBetweenDates(student, LocalDate.of(2021, 01, 01), LocalDate.of(2021, 01, 05))
		.size();

	assertEquals(2, actual);
    }

    @Test
    void givenStudentAndDate_whenGetdByStudentAndDate_thenFound() {
	Student student = Student.builder().id(1).build();

	int actual = lessonDao.getByStudentAndDate(student, LocalDate.of(2021, 01, 01)).size();

	assertEquals(2, actual);
    }

    @Test
    void givenLesson_whenGetByDateBAndTeacherAndLessonTime_thenFound() {
	Lesson lesson = getLesson();

	Lesson actual = lessonDao
		.getByDateAndTeacherAndLessonTime(lesson.getDate(), lesson.getTeacher(), lesson.getLessonTime()).get();

	assertEquals(lesson.getDate(), actual.getDate());
	assertEquals(lesson.getTeacher().getId(), actual.getTeacher().getId());
	assertEquals(lesson.getLessonTime().getId(), actual.getLessonTime().getId());
    }

    @Test
    void givenLesson_whenGetByDateAndAudienceAndLessonTime_thenFound() {
	Lesson lesson = getLesson();

	Lesson actual = lessonDao
		.getByDateAndAudienceAndLessonTime(lesson.getDate(), lesson.getAudience(), lesson.getLessonTime())
		.get();

	assertEquals(lesson.getDate(), actual.getDate());
	assertEquals(lesson.getAudience().getId(), actual.getAudience().getId());
	assertEquals(lesson.getLessonTime().getId(), actual.getLessonTime().getId());
    }

    @Test
    void givenDateLessonTimeAndGroup_whenGetByDateAndLessonTimeAndGroup_thenFound() {
	Lesson lesson = getLesson();

	Lesson actual = lessonDao
		.getByDateAndLessonTimeAndGroup(lesson.getDate(), lesson.getLessonTime(), lesson.getGroups().get(0))
		.get();

	assertEquals(lesson.getDate(), actual.getDate());
	assertEquals(lesson.getLessonTime().getId(), actual.getLessonTime().getId());
	assertEquals(lesson.getGroups().get(0).getId(), actual.getGroups().get(0).getId());
    }

    @Test
    void givenLessons_whenCount_thenCounted() {

	int actual = lessonDao.count();

	assertEquals(6, actual);
    }

    private Lesson getLesson() {
	LocalDate date = LocalDate.of(2021, 01, 01);
	LessonTime lessonTime = LessonTime.builder().id(1).orderNumber("first lesson").startTime(LocalTime.of(8, 30))
		.endTime(LocalTime.of(9, 15)).build();
	Subject subject = Subject.builder().id(1).name("subject 1").build();
	Teacher teacher1 = Teacher.builder().id(1).firstName("first name 1").lastName("last name 1")
		.birthDate(LocalDate.of(2000, 01, 01)).phone("0670000001").address("address 1").email("email 1")
		.degree("0").gender(Gender.MALE).build();
	subject.setTeachers(Arrays.asList(teacher1));
	Audience audience = Audience.builder().id(1).number("1").capacity(300).description("description1").build();
	Year year = Year.builder().id(1).name("year 1").build();
	Group group1 = Group.builder().id(1).name("group 1").year(year).build();
	Group group2 = Group.builder().id(2).name("group 2").year(year).build();
	return Lesson.builder().id(1).date(date).lessonTime(lessonTime).subject(subject).audience(audience)
		.teacher(teacher1).groups(Arrays.asList(group1, group2)).build();
    }

}
