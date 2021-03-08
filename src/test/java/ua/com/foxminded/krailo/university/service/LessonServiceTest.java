package ua.com.foxminded.krailo.university.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
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
import ua.com.foxminded.krailo.university.exception.ServiceException;
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
	when(lessonDao.findByDateAndTeacherAndLessonTime(lesson)).thenReturn(Optional.empty());
	when(lessonDao.findByDateAndAudienceAndLessonTime(lesson)).thenReturn(Optional.empty());
	when(vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()))
		.thenReturn(Optional.empty());
	when(holidayDao.findByDate(lesson.getDate())).thenReturn(Optional.empty());
	when(lessonDao.findByDateAndLessonTimeIdAndGroupId(lesson.getDate(), lesson.getLessonTime().getId(),
		lesson.getGroups().get(0).getId())).thenReturn(Optional.empty());

	lessonService.create(lesson);

	verify(lessonDao).create(lesson);
    }

    @Test
    void givenLessonWithBookedAudience_whenCreate_thenTrowServiceException() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateAndAudienceAndLessonTime(lesson))
		.thenReturn(Optional.of(Lesson.builder().id(1).build()));

	assertThrows(ServiceException.class, () -> lessonService.create(lesson));
    }

    @Test
    void givenLessonWithBookedTeacher_whenCreate_thenTrowServiceException() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateAndTeacherAndLessonTime(lesson))
		.thenReturn(Optional.of(Lesson.builder().id(1).build()));

	assertThrows(ServiceException.class, () -> lessonService.create(lesson));
    }

    @Test
    void givenLessonWithAudienceCapacityLessThenStuentsAmount_whenCreate_thenTrowServiceException() {
	Lesson lesson = createLesson();
	lesson.getAudience().setCapacity(1);

	assertThrows(ServiceException.class, () -> lessonService.create(lesson));
    }

    @Test
    void givenLessonWhithTeacherOnVocation_whenCreate_thenTrowServiceException() {
	Lesson lesson = createLesson();
	when(vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()))
		.thenReturn(Optional.of(Vocation.builder().id(1).build()));

	assertThrows(ServiceException.class, () -> lessonService.create(lesson));
    }

    @Test
    void givenLessonWhithDateThatMatchToHoliday_whenCreate_thenThrowServiceException() {
	Lesson lesson = createLesson();
	when(holidayDao.findByDate(lesson.getDate())).thenReturn(Optional.of(Holiday.builder().id(1).build()));

	assertThrows(ServiceException.class, () -> lessonService.create(lesson));
    }

    @Test
    void givenLessonWhithDateThatMatchToWeekend_whenCreate_thenThrowServiceException() {
	Lesson lesson = createLesson();
	lesson.setDate(LocalDate.of(2021, 01, 02));

	assertThrows(ServiceException.class, () -> lessonService.create(lesson));
    }

    @Test
    void givenLessonWhithTeacherThatNotTeachesLessonSubject_whenCreate_thenThrowServiceException() {
	Lesson lesson = createLesson();
	lesson.getTeacher().getSubjects().remove(0);

	assertThrows(ServiceException.class, () -> lessonService.create(lesson));
    }

    @Test
    void givenLessonWhithGroupThatNotFree_whenCreate_thenThrowServiceException() {
	Lesson lesson = createLesson();
	lesson.getTeacher().getSubjects().add(Subject.builder().id(1).name("subject1").build());
	when(lessonDao.findByDateAndLessonTimeIdAndGroupId(lesson.getDate(), lesson.getLessonTime().getId(),
		lesson.getGroups().get(0).getId())).thenReturn(Optional.of(createLesson()));

	assertThrows(ServiceException.class, () -> lessonService.create(lesson));
    }

    @Test
    void givenLessonWhithAllCorrectFields_whenUpdate_thenUpdated() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateAndTeacherAndLessonTime(lesson)).thenReturn(Optional.empty());
	when(lessonDao.findByDateAndAudienceAndLessonTime(lesson)).thenReturn(Optional.empty());
	when(vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()))
		.thenReturn(Optional.empty());
	when(holidayDao.findByDate(lesson.getDate())).thenReturn(Optional.empty());
	when(lessonDao.findByDateAndLessonTimeIdAndGroupId(lesson.getDate(), lesson.getLessonTime().getId(),
		lesson.getGroups().get(0).getId())).thenReturn(Optional.empty());

	lessonService.update(lesson);

	verify(lessonDao).update(lesson);
    }

    @Test
    void givenLessonWithBookedAudience_whenUpdate_thenTrowServiceWxception() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateAndAudienceAndLessonTime(lesson))
		.thenReturn(Optional.of(Lesson.builder().id(1).build()));

	assertThrows(ServiceException.class, () -> lessonService.update(lesson));
    }

    @Test
    void givenLessonWithBookedTeacher_whenUpdate_thenTrowServiceException() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateAndTeacherAndLessonTime(lesson))
		.thenReturn(Optional.of(Lesson.builder().id(1).build()));

	assertThrows(ServiceException.class, () -> lessonService.update(lesson));
    }

    @Test
    void givenLessonWithAudienceCapacityLessThenStuentsAmount_whenUpdate_thenThrowServiceException() {
	Lesson lesson = createLesson();
	lesson.getAudience().setCapacity(1);

	assertThrows(ServiceException.class, () -> lessonService.update(lesson));
    }

    @Test
    void givenLessonWhithTeacherOnVocation_whenUpdate_thenThrowServiceException() {
	Lesson lesson = createLesson();
	when(vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()))
		.thenReturn(Optional.of(Vocation.builder().id(1).build()));

	assertThrows(ServiceException.class, () -> lessonService.update(lesson));
    }

    @Test
    void givenLessonWhithDateThatMatchToHoliday_whenUpdate_thenTrowsServiceException() {
	Lesson lesson = createLesson();
	when(holidayDao.findByDate(lesson.getDate())).thenReturn(Optional.of(Holiday.builder().id(1).build()));

	assertThrows(ServiceException.class, () -> lessonService.update(lesson));
    }

    @Test
    void givenLessonWhithDateThatMatchToWeekend_whenUpdate_thenTrowsServiceException() {
	Lesson lesson = createLesson();
	lesson.setDate(LocalDate.of(2021, 01, 02));

	assertThrows(ServiceException.class, () -> lessonService.update(lesson));
    }

    @Test
    void givenLessonWhithTeacherThatNotTeachesLessonSubject_whenUpdate_thenThrowServiceException() {
	Lesson lesson = createLesson();
	lesson.getTeacher().getSubjects().remove(0);

	assertThrows(ServiceException.class, () -> lessonService.update(lesson));
    }

    @Test
    void givenLessonWhithGroupThatNotFree_whenUpdate_thenThrowServiceException() {
	Lesson lesson = createLesson();
	when(lessonDao.findByDateAndLessonTimeIdAndGroupId(lesson.getDate(), lesson.getLessonTime().getId(),
		lesson.getGroups().get(0).getId())).thenReturn(Optional.of(createLesson()));

	assertThrows(ServiceException.class, () -> lessonService.update(lesson));
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
