package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ua.com.foxminded.krailo.university.config.ConfigTest;
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.LessonsTimeSchedule;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Timetable;
import ua.com.foxminded.krailo.university.model.Year;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
class LessonServiceTest {

    @InjectMocks
    private LessonService lessonService;
    @Mock
    private LessonDao lessonDao;

    @Test
    void givenLessonId_whenGetById_thenGot() {
	Lesson lesson = new Lesson(1, LocalDate.of(2021, 01, 01), new LessonTime(), new Subject(), new Audience(),
		new Teacher(), null);
	when(lessonDao.findById(1)).thenReturn(lesson);

	Lesson actual = lessonService.getById(1);

	Lesson expected = new Lesson(1, LocalDate.of(2021, 01, 01), new LessonTime(), new Subject(), new Audience(),
		new Teacher(), null);
	assertEquals(expected, actual);
    }

    @Test
    void givenLessons_whenGetAll_thenGotAll() {
	List<Lesson> lessons = new ArrayList<>(Arrays.asList(new Lesson(1, LocalDate.of(2021, 01, 01), new LessonTime(), new Subject(), new Audience(),
		new Teacher(), null)));
	when(lessonDao.findAll()).thenReturn(lessons);

	List<Lesson> actual = lessonService.getAll();

	List<Lesson> expected = new ArrayList<>(Arrays.asList(new Lesson(1, LocalDate.of(2021, 01, 01), new LessonTime(), new Subject(), new Audience(),
		new Teacher(), null)));
	assertEquals(expected, actual);
    }

    @Test
    void givenLessonId_whenDeleteById_thenDeleted() {
	Lesson lesson = new Lesson(1, LocalDate.of(2021, 01, 01), new LessonTime(), new Subject(), new Audience(),
		new Teacher(), null);
	doNothing().when(lessonDao).deleteById(1);

	lessonService.delete(lesson);

	verify(lessonDao).deleteById(1);
    }

    @Test
    void givenLessonWhithAllCorrectFields_whenCreate_thenCreated() {
	List<Lesson> lessons = createLessons();
	Lesson lesson = new Lesson(3, LocalDate.of(2021, 01, 01), new LessonTime(), lessons.get(1).getSubject(),
		new Audience(), new Teacher(), lessons.get(1).getTimetable());
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);

	lessonService.create(lesson);

	verify(lessonDao).create(lesson);
    }

    @Test
    void givenLessonWithRepeatedLessonTime_whenCreate_thenNotCreated() {
	List<Lesson> lessons = createLessons();
	Lesson lesson = new Lesson(3, LocalDate.of(2021, 01, 01), lessons.get(1).getLessonTime(),
		lessons.get(1).getSubject(), lessons.get(1).getAudience(), new Teacher(),
		lessons.get(1).getTimetable());
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);

	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }

    @Test
    void givenLessonWithWrongSubject_whenCreate_thenNotCreated() {
	List<Lesson> lessons = createLessons();
	Lesson lesson = new Lesson(3, LocalDate.of(2021, 01, 01), new LessonTime(), new Subject(), new Audience(),
		new Teacher(), lessons.get(1).getTimetable());
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);

	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }

    @Test
    void givenLessonWithWrongTeacher_whenCreate_thenNotCreated() {
	List<Lesson> lessons = createLessons();
	Lesson lesson = new Lesson(3, LocalDate.of(2021, 01, 01), lessons.get(1).getLessonTime(),
		lessons.get(1).getSubject(), new Audience(), lessons.get(1).getTeacher(),
		lessons.get(1).getTimetable());
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);

	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }

    @Test
    void givenLessonWithWrongGroup_whenCreate_thenNotCreated() {
	List<Lesson> lessons = createLessons();
	Lesson lesson = new Lesson(3, LocalDate.of(2021, 01, 01), new LessonTime(), lessons.get(1).getSubject(),
		lessons.get(1).getAudience(), new Teacher(), lessons.get(1).getTimetable());
	lesson.getGroups().add(new Group());
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);

	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }

    @Test
    void givenLessonWithWrongAudienceCapacity_whenCreate_thenNotCreated() {
	List<Lesson> lessons = createLessons();
	Lesson lesson = new Lesson(3, LocalDate.of(2021, 01, 01), new LessonTime(), lessons.get(1).getSubject(),
		new Audience("new", null, 1, ""), new Teacher(), lessons.get(1).getTimetable());
	lesson.getGroups().add(lessons.get(0).getGroups().get(0));
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);

	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }

    @Test
    void givenLessonWhithAllCorrectFields_whenUpdate_thenUpdated() {
	List<Lesson> lessons = createLessons();
	Lesson lesson = new Lesson(1, LocalDate.of(2021, 01, 01), new LessonTime(), lessons.get(1).getSubject(),
		new Audience(), new Teacher(), lessons.get(1).getTimetable());
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);

	lessonService.update(lesson);

	verify(lessonDao).update(lesson);
    }

    @Test
    void givenLessonWithRepeatedLessonTime_whenUpdate_thenUpdated() {
	List<Lesson> lessons = createLessons();
	Lesson lesson = new Lesson(3, LocalDate.of(2021, 01, 01), lessons.get(1).getLessonTime(),
		lessons.get(1).getSubject(), lessons.get(1).getAudience(), new Teacher(),
		lessons.get(1).getTimetable());
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);

	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }

    @Test
    void givenLessonWithWrongSubject_whenUpdate_thenUpdated() {
	List<Lesson> lessons = createLessons();
	Lesson lesson = new Lesson(3, LocalDate.of(2021, 01, 01), new LessonTime(), new Subject(), new Audience(),
		new Teacher(), lessons.get(1).getTimetable());
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);

	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }

    @Test
    void givenLessonWithWrongTeacher_whenUpdate_thenUpdated() {
	List<Lesson> lessons = createLessons();
	Lesson lesson = new Lesson(3, LocalDate.of(2021, 01, 01), lessons.get(1).getLessonTime(),
		lessons.get(1).getSubject(), new Audience(), lessons.get(1).getTeacher(),
		lessons.get(1).getTimetable());
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);

	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }

    @Test
    void givenLessonWithWrongGroup_whenUpdate_thenUpdated() {
	List<Lesson> lessons = createLessons();
	Lesson lesson = new Lesson(3, LocalDate.of(2021, 01, 01), new LessonTime(), lessons.get(1).getSubject(),
		lessons.get(1).getAudience(), new Teacher(), lessons.get(1).getTimetable());
	lesson.getGroups().add(new Group());
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);

	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }

    @Test
    void givenLessonWithWrongAudienceCapacity_whenUpdate_thenUpdated() {
	List<Lesson> lessons = createLessons();
	Lesson lesson = new Lesson(3, LocalDate.of(2021, 01, 01), new LessonTime(), lessons.get(1).getSubject(),
		new Audience("new", null, 1, ""), new Teacher(), lessons.get(1).getTimetable());
	lesson.getGroups().add(lessons.get(0).getGroups().get(0));
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);

	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }

    private List<Lesson> createLessons() {
	List<Lesson> lessons = new ArrayList<>();
	Year year = new Year();
	Group group1 = new Group(1);
	Group group2 = new Group(2);
	group1.setStudents(Arrays.asList(new Student(1), new Student(2)));
	group2.setStudents(Arrays.asList(new Student(3), new Student(4)));
	year.setGroups(Arrays.asList(group1, group2));
	Subject subject1 = new Subject(1, "sub1");
	Subject subject2 = new Subject(2, "sub2");
	year.setSubjects(Arrays.asList(subject1, subject2));
	Timetable timetable = new Timetable(1, "timetable", year);
	Audience audience = new Audience(1, "one", null, 4, "");
	LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule(1, "lesson time schedule");
	LessonTime lessonTime1 = new LessonTime(1, "first", LocalTime.of(8, 45), LocalTime.of(9, 30),
		lessonsTimeSchedule);
	LessonTime lessonTime2 = new LessonTime(2, "second", LocalTime.of(9, 45), LocalTime.of(10, 30),
		lessonsTimeSchedule);
	Teacher teacher1 = new Teacher(1);
	Teacher teacher2 = new Teacher(2);
	Lesson lesson1 = new Lesson(1, LocalDate.of(2021, 01, 01), lessonTime1, subject1, audience, teacher1,
		timetable);
	lesson1.getGroups().add(group1);
	lesson1.getGroups().add(group2);
	Lesson lesson2 = new Lesson(2, LocalDate.of(2021, 01, 01), lessonTime2, subject2, audience, teacher2,
		timetable);
	lesson2.getGroups().add(group1);
	lesson2.getGroups().add(group2);
	lessons.add(lesson1);
	lessons.add(lesson2);
	return lessons;
    }

}
