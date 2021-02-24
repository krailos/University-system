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

import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.VocationDao;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Building;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Holiday;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.LessonsTimeSchedule;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Vocation;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @InjectMocks
    private LessonService lessonService;
    @Mock
    private LessonDao lessonDao;
    @Mock
    private VocationDao vocationDao;
    @Mock
    private HolidayDao holidayDao;

    @Test
    void givenLessonId_whenGetById_thenGot() {
	Lesson lesson = createLesson();
	when(lessonDao.findById(1)).thenReturn(lesson);

	Lesson actual = lessonService.getById(1);

	Lesson expected = createLesson();
	assertEquals(expected, actual);
    }

    @Test
    void givenLessons_whenGetAll_thenGotAll() {
	List<Lesson> lessons = createLessons();
	when(lessonDao.findAll()).thenReturn(lessons);

	List<Lesson> actual = lessonService.getAll();

	List<Lesson> expected = createLessons();
	assertEquals(expected, actual);
    }

    @Test
    void givenLessonId_whenDeleteById_thenDeleted() {
	Lesson lesson = createLesson();
	doNothing().when(lessonDao).deleteById(1);

	lessonService.delete(lesson);

	verify(lessonDao).deleteById(1);
    }

    @Test
    void givenLessonWhithAllCorrectFields_whenCreate_thenCreated() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateByTeacherByLessonTime(lesson)).thenReturn(null);
	when(lessonDao.findByDateByAudienceByLessonTime(lesson)).thenReturn(null);
	when(vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate())).thenReturn(null);
	when(holidayDao.findByDate(lesson.getDate())).thenReturn(null);

	lessonService.create(lesson);

	verify(lessonDao).create(lesson);
    }

    @Test
    void givenLessonWithBookedAudience_whenCreate_thenNotCreated() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateByAudienceByLessonTime(lesson)).thenReturn(Lesson.builder().id(1).build());

	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }

    @Test
    void givenLessonWithBookedTeacher_whenCreate_thenNotCreated() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateByTeacherByLessonTime(lesson)).thenReturn(Lesson.builder().id(1).build());

	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }

    @Test
    void givenLessonWithAudienceCapacityLessThenStuentsAmount_whenCreate_thenNotCreated() {
	Lesson lesson = createLesson();
	lesson.getAudience().setCapacity(1);

	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }

    @Test
    void givenLessonWhithTeacherOnVocation_whenCreate_thenNotCreated() {
	Lesson lesson = createLesson();
	when(vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()))
		.thenReturn(Vocation.builder().id(1).build());

	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }

    @Test
    void givenLessonWhithDateThatMatchToHoliday_whenCreate_thenNotCreated() {
	Lesson lesson = createLesson();
	when(holidayDao.findByDate(lesson.getDate())).thenReturn(Holiday.builder().id(1).build());

	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }

    @Test
    void givenLessonWhithDateThatMatchToWeekend_whenCreate_thenNotCreated() {
	Lesson lesson = createLesson();
	lesson.setDate(LocalDate.of(2021, 01, 02));

	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }

    @Test
    void givenLessonWhithAllCorrectFields_whenUpdate_thenUpdated() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateByTeacherByLessonTime(lesson)).thenReturn(null);
	when(lessonDao.findByDateByAudienceByLessonTime(lesson)).thenReturn(null);
	when(vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate())).thenReturn(null);
	when(holidayDao.findByDate(lesson.getDate())).thenReturn(null);

	lessonService.update(lesson);

	verify(lessonDao).update(lesson);
    }

    @Test
    void givenLessonWithBookedAudience_whenUpdate_thenNotUpdated() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateByAudienceByLessonTime(lesson)).thenReturn(Lesson.builder().id(1).build());

	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }

    @Test
    void givenLessonWithBookedTeacher_whenUpdate_thenNotUpdated() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateByTeacherByLessonTime(lesson)).thenReturn(Lesson.builder().id(1).build());

	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }

    @Test
    void givenLessonWithAudienceCapacityLessThenStuentsAmount_whenUpdate_thenNotUpdated() {
	Lesson lesson = createLesson();
	lesson.getAudience().setCapacity(1);

	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }

    @Test
    void givenLessonWhithTeacherOnVocation_whenUpdate_thenNotUpdated() {
	Lesson lesson = createLesson();
	when(vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()))
		.thenReturn(Vocation.builder().id(1).build());

	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }

    @Test
    void givenLessonWhithDateThatMatchToHoliday_whenUpdate_thenNotUpdated() {
	Lesson lesson = createLesson();
	when(holidayDao.findByDate(lesson.getDate())).thenReturn(Holiday.builder().id(1).build());

	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }

    @Test
    void givenLessonWhithDateThatMatchToWeekend_whenUpdate_thenNotUpdated() {
	Lesson lesson = createLesson();
	lesson.setDate(LocalDate.of(2021, 01, 02));

	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }

    private Lesson createLesson() {
	Student student1 = Student.builder().id(1).build();
	Student student2 = Student.builder().id(2).build();
	Student student3 = Student.builder().id(3).build();
	Student student4 = Student.builder().id(4).build();
	Subject subject1 = Subject.builder().id(1).name("subject1").build();
	Teacher teacher1 = Teacher.builder().id(1).build();
	LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule.LessonsTimescheduleBuilder().id(1).build();
	Group group1 = Group.builder().id(1).name("group1").students(Arrays.asList(student1, student2)).build();
	Group group2 = Group.builder().id(2).name("group2").students(Arrays.asList(student3, student4)).build();
	LessonTime lessonTime1 = LessonTime.builder().id(1).orderNumber("order number 1").startTime(LocalTime.of(8, 45))
		.endTime(LocalTime.of(9, 30)).lessonsTimeSchedule(lessonsTimeSchedule).build();
	Audience audience = Audience.builder().id(1).number("number 1").building(Building.builder().id(1).build())
		.capacity(4).description("description").build();
	Lesson lesson = Lesson.builder().id(1).date(LocalDate.of(2021, 01, 01)).lessonTime(lessonTime1)
		.subject(subject1).audience(audience).teacher(teacher1).groups(Arrays.asList(group1, group2)).build();
	return lesson;
    }

    private List<Lesson> createLessons() {
	Student student1 = Student.builder().id(1).build();
	Student student2 = Student.builder().id(2).build();
	Student student3 = Student.builder().id(3).build();
	Student student4 = Student.builder().id(4).build();
	Subject subject1 = Subject.builder().id(1).name("subject1").build();
	Subject subject2 = Subject.builder().id(2).name("subject2").build();
	Teacher teacher1 = Teacher.builder().id(1).build();
	Teacher teacher2 = Teacher.builder().id(2).build();
	LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule.LessonsTimescheduleBuilder().id(1).build();
	Group group1 = Group.builder().id(1).name("group1").students(Arrays.asList(student1, student2)).build();
	Group group2 = Group.builder().id(2).name("group2").students(Arrays.asList(student3, student4)).build();
	LessonTime lessonTime1 = LessonTime.builder().id(1).orderNumber("order number 1").startTime(LocalTime.of(8, 45))
		.endTime(LocalTime.of(9, 30)).lessonsTimeSchedule(lessonsTimeSchedule).build();
	LessonTime lessonTime2 = LessonTime.builder().id(1).orderNumber("order number 2").startTime(LocalTime.of(9, 45))
		.endTime(LocalTime.of(10, 30)).lessonsTimeSchedule(lessonsTimeSchedule).build();
	Audience audience = Audience.builder().id(1).number("number 1").building(Building.builder().id(1).build())
		.capacity(4).description("description").build();
	Lesson lesson1 = Lesson.builder().id(1).date(LocalDate.of(2021, 01, 01)).lessonTime(lessonTime1)
		.subject(subject1).audience(audience).teacher(teacher1).groups(Arrays.asList(group1, group2)).build();
	Lesson lesson2 = Lesson.builder().id(2).date(LocalDate.of(2021, 01, 01)).lessonTime(lessonTime2)
		.subject(subject2).teacher(teacher2).groups(Arrays.asList(group1, group2)).build();
	return new ArrayList<Lesson>(Arrays.asList(lesson1, lesson2));
    }

}
