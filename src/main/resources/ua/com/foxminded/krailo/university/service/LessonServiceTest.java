package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.dao.VocationDao;
import ua.com.foxminded.krailo.university.exception.AudienceNotFreeException;
import ua.com.foxminded.krailo.university.exception.AudienceOverflowException;
import ua.com.foxminded.krailo.university.exception.GroupNotFreeException;
import ua.com.foxminded.krailo.university.exception.LessonDateOnHolidayException;
import ua.com.foxminded.krailo.university.exception.LessonDateOnWeekendException;
import ua.com.foxminded.krailo.university.exception.TeacherNotFreeException;
import ua.com.foxminded.krailo.university.exception.TeacherNotTeachLessonException;
import ua.com.foxminded.krailo.university.exception.TeacherOnVocationException;
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
    @Mock
    private TeacherDao teacherDao;

    @Test
    void givenLessonId_whenGetById_thenGot() {
	Lesson lesson = createLesson();
	when(lessonDao.findById(1)).thenReturn(Optional.of(lesson));

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
	when(lessonDao.findByDateAndTeacherIdAndLessonTimeId(lesson.getDate(), lesson.getTeacher().getId(),
		lesson.getLessonTime().getId())).thenReturn(Optional.empty());
	when(lessonDao.findByDateAndAudienceIdAndLessonTimeId(lesson.getDate(), lesson.getAudience().getId(),
		lesson.getLessonTime().getId())).thenReturn(Optional.empty());
	when(vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()))
		.thenReturn(Optional.empty());
	when(holidayDao.findByDate(lesson.getDate())).thenReturn(Optional.empty());
	when(lessonDao.findByDateAndLessonTimeIdAndGroupId(lesson.getDate(), lesson.getLessonTime().getId(),
		lesson.getGroups().get(0).getId())).thenReturn(Optional.empty());

	lessonService.create(lesson);

	verify(lessonDao).create(lesson);
    }

    @Test
    void givenLessonWhithAllCorrectFields_whenUpdate_thenUpdated() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateAndTeacherIdAndLessonTimeId(lesson.getDate(), lesson.getTeacher().getId(),
		lesson.getLessonTime().getId())).thenReturn(Optional.empty());
	when(lessonDao.findByDateAndAudienceIdAndLessonTimeId(lesson.getDate(), lesson.getAudience().getId(),
		lesson.getLessonTime().getId())).thenReturn(Optional.empty());
	when(vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()))
		.thenReturn(Optional.empty());
	when(holidayDao.findByDate(lesson.getDate())).thenReturn(Optional.empty());
	when(lessonDao.findByDateAndLessonTimeIdAndGroupId(lesson.getDate(), lesson.getLessonTime().getId(),
		lesson.getGroups().get(0).getId())).thenReturn(Optional.empty());

	lessonService.update(lesson);

	verify(lessonDao).update(lesson);
    }

    @Test
    void givenLessonInWhichAudienceIsBooked_whenCreate_thenAudienceNotFreeExceptionThrown() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateAndAudienceIdAndLessonTimeId(lesson.getDate(), lesson.getAudience().getId(),
		lesson.getLessonTime().getId())).thenReturn(Optional.of(Lesson.builder().id(2).build()));

	Exception exception = assertThrows(AudienceNotFreeException.class, () -> lessonService.create(lesson));

	String expectedMessage = "Audience not free, audienceId=1";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenLessonInWhichAudienceIsBooked_whenUpdate_thenAudienceNotFreeExceptionThrown() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateAndAudienceIdAndLessonTimeId(lesson.getDate(), lesson.getAudience().getId(),
		lesson.getLessonTime().getId())).thenReturn(Optional.of(Lesson.builder().id(2).build()));

	Exception exception = assertThrows(AudienceNotFreeException.class, () -> lessonService.update(lesson));

	String expectedMessage = "Audience not free, audienceId=1";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenLessonInWhichAudienceIsBookedByTheSameLesson_whenUpdate_thenUpdated() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateAndAudienceIdAndLessonTimeId(lesson.getDate(), lesson.getAudience().getId(),
		lesson.getLessonTime().getId())).thenReturn(Optional.of(Lesson.builder().id(1).build()));

	lessonService.update(lesson);

	verify(lessonDao).update(lesson);
    }

    @Test
    void givenLessonInWhichTeacherIsBooked_whenCreate_thenTeacherNotFreeExceptionThrown() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateAndTeacherIdAndLessonTimeId(lesson.getDate(), lesson.getTeacher().getId(),
		lesson.getLessonTime().getId())).thenReturn(Optional.of(Lesson.builder().id(2).build()));

	Exception exception = assertThrows(TeacherNotFreeException.class, () -> lessonService.create(lesson));

	String expectedMessage = "Teacher not free, teacherId=1";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenLessonInWhichTeacherIsBooked_whenUpdate_thenTeacherNotFreeExceptionThrown() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateAndTeacherIdAndLessonTimeId(lesson.getDate(), lesson.getTeacher().getId(),
		lesson.getLessonTime().getId())).thenReturn(Optional.of(Lesson.builder().id(2).build()));

	Exception exception = assertThrows(TeacherNotFreeException.class, () -> lessonService.update(lesson));

	String expectedMessage = "Teacher not free, teacherId=1";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenLessonInWhichTeacherIsBookedByTheSameLesson_whenUpdate_thenUpdated() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateAndTeacherIdAndLessonTimeId(lesson.getDate(), lesson.getTeacher().getId(),
		lesson.getLessonTime().getId())).thenReturn(Optional.of(Lesson.builder().id(1).build()));

	lessonService.update(lesson);

	verify(lessonDao).update(lesson);
    }

    @Test
    void givenLessonInWhihcAudienceCapacityLessThenStuentsAmount_whenCreate_thenAudienceOverflowExceptionThrown() {
	Lesson lesson = createLesson();
	lesson.getAudience().setCapacity(1);

	Exception exception = assertThrows(AudienceOverflowException.class, () -> lessonService.create(lesson));

	String expectedMessage = "audience is owerflowed, audience capacity=1";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenLessonInWhihcAudienceCapacityLessThenStuentsAmount_whenUpdate_thenAudienceOverflowExceptionThrown() {
	Lesson lesson = createLesson();
	lesson.getAudience().setCapacity(1);

	Exception exception = assertThrows(AudienceOverflowException.class, () -> lessonService.update(lesson));

	String expectedMessage = "audience is owerflowed, audience capacity=1";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenLessonInWhichTeacherIsOnVocation_whenCreate_thenTeacherOnVocationExceptionThrown() {
	Lesson lesson = createLesson();
	when(vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()))
		.thenReturn(Optional.of(Vocation.builder().id(1).build()));

	Exception exception = assertThrows(TeacherOnVocationException.class, () -> lessonService.create(lesson));

	String expectedMessage = "teacher on vocation, teacherId=1";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenLessonInWhichTeacherIsOnVocation_whenUpdate_thenTeacherOnVocationExceptionThrown() {
	Lesson lesson = createLesson();
	when(vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()))
		.thenReturn(Optional.of(Vocation.builder().id(1).build()));

	Exception exception = assertThrows(TeacherOnVocationException.class, () -> lessonService.update(lesson));

	String expectedMessage = "teacher on vocation, teacherId=1";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenLessonOnHoliday_whenCreate_thenLessonDateOnHolidayExceptionThrown() {
	Lesson lesson = createLesson();
	when(holidayDao.findByDate(lesson.getDate())).thenReturn(Optional.of(Holiday.builder().id(1).build()));

	Exception exception = assertThrows(LessonDateOnHolidayException.class, () -> lessonService.create(lesson));

	String expectedMessage = "lesson date=2021-01-01 is holiday";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenLessonOnHoliday_whenUpdate_thenLessonDateOnHolidayExceptionThrown() {
	Lesson lesson = createLesson();
	when(holidayDao.findByDate(lesson.getDate())).thenReturn(Optional.of(Holiday.builder().id(1).build()));

	Exception exception = assertThrows(LessonDateOnHolidayException.class, () -> lessonService.update(lesson));

	String expectedMessage = "lesson date=2021-01-01 is holiday";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenLessonOnWeekend_whenCreate_thenLessonDateOnHolidayExceptionThrown() {
	Lesson lesson = createLesson();
	LocalDate nextSunday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
	lesson.setDate(nextSunday);

	Exception exception = assertThrows(LessonDateOnWeekendException.class, () -> lessonService.create(lesson));

	String expectedMessage = "lesson date is weekend=SUNDAY";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenLessonOnWeekend_whenUpdae_thenLessonDateOnHolidayExceptionThrown() {
	Lesson lesson = createLesson();
	LocalDate nextSunday = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
	lesson.setDate(nextSunday);

	Exception exception = assertThrows(LessonDateOnWeekendException.class, () -> lessonService.update(lesson));

	String expectedMessage = "lesson date is weekend=SUNDAY";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenLessonWhithTeacherThatNotTeachesLessonSubject_whenCreate_thenTeacherNotTeachLessonExceptionThrown() {
	Lesson lesson = createLesson();
	lesson.getTeacher().getSubjects().remove(0);

	Exception exception = assertThrows(TeacherNotTeachLessonException.class, () -> lessonService.create(lesson));

	String expectedMessage = "teacher dosn't teach lesson's subject";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenLessonWhithTeacherThatNotTeachesLessonSubject_whenUpdate_thenTeacherNotTeachLessonExceptionThrown() {
	Lesson lesson = createLesson();
	lesson.getTeacher().getSubjects().remove(0);

	Exception exception = assertThrows(TeacherNotTeachLessonException.class, () -> lessonService.update(lesson));

	String expectedMessage = "teacher dosn't teach lesson's subject";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenLessonInWhichGroupIsBooked_whenCreate_thenGroupNotFreeExceptionThrown() {
	Lesson lesson = createLesson();
	lesson.setId(2);
	lesson.getTeacher().getSubjects().add(Subject.builder().id(1).name("subject1").build());
	when(lessonDao.findByDateAndLessonTimeIdAndGroupId(lesson.getDate(), lesson.getLessonTime().getId(),
		lesson.getGroups().get(0).getId())).thenReturn(Optional.of(createLesson()));

	Exception exception = assertThrows(GroupNotFreeException.class, () -> lessonService.create(lesson));

	String expectedMessage = "lesson groups are not free";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void givenLessonInWhichGroupIsBooked_whenUpdate_thenGroupNotFreeExceptionThrown() {
	Lesson lesson = createLesson();
	lesson.setId(2);
	lesson.getTeacher().getSubjects().add(Subject.builder().id(1).name("subject1").build());
	when(lessonDao.findByDateAndLessonTimeIdAndGroupId(lesson.getDate(), lesson.getLessonTime().getId(),
		lesson.getGroups().get(0).getId())).thenReturn(Optional.of(createLesson()));

	Exception exception = assertThrows(GroupNotFreeException.class, () -> lessonService.update(lesson));

	String expectedMessage = "lesson groups are not free";
	String actualMessage = exception.getMessage();
	assertEquals(expectedMessage, actualMessage);
    }

    private Lesson createLesson() {
	Student student1 = Student.builder().id(1).build();
	Student student2 = Student.builder().id(2).build();
	Student student3 = Student.builder().id(3).build();
	Student student4 = Student.builder().id(4).build();
	Subject subject1 = Subject.builder().id(1).name("subject1").build();
	Teacher teacher1 = Teacher.builder().id(1).build();
	teacher1.getSubjects().add(subject1);
	LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule.LessonsTimescheduleBuilder().id(1).build();
	Group group1 = Group.builder().id(1).name("group1").students(Arrays.asList(student1, student2)).build();
	Group group2 = Group.builder().id(2).name("group2").students(Arrays.asList(student3, student4)).build();
	LessonTime lessonTime1 = LessonTime.builder().id(1).orderNumber("order number 1").startTime(LocalTime.of(8, 45))
		.endTime(LocalTime.of(9, 30)).lessonsTimeSchedule(lessonsTimeSchedule).build();
	Audience audience = Audience.builder().id(1).number("number 1").building(Building.builder().id(1).build())
		.capacity(5).description("description").build();
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