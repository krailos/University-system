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
import ua.com.foxminded.krailo.university.model.Speciality;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Timetable;
import ua.com.foxminded.krailo.university.model.Vocation;
import ua.com.foxminded.krailo.university.model.Year;

@ExtendWith(MockitoExtension.class)
@SpringJUnitConfig(ConfigTest.class)
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
	Lesson lesson = new Lesson.LessonBuilder().withId(1).withDate(LocalDate.of(2021, 01, 01)).built();
	when(lessonDao.findById(1)).thenReturn(lesson);

	Lesson actual = lessonService.getById(1);

	Lesson expected = new Lesson.LessonBuilder().withId(1).withDate(LocalDate.of(2021, 01, 01)).built();
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
	Lesson lesson = new Lesson.LessonBuilder().withId(1).withDate(LocalDate.of(2021, 01, 01)).built();
	doNothing().when(lessonDao).deleteById(1);

	lessonService.delete(lesson);

	verify(lessonDao).deleteById(1);
    }

    @Test
    void givenLessonWhithAllCorrectFields_whenCreate_thenCreated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate().plusDays(1)).
		withEndDate(lessons.get(0).getDate().plusDays(7)).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate().plusDays(1)).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(3).built()).
		withSubject(lessons.get(0).getSubject()).
		withAudience(lessons.get(0).getAudience()).
		withTeacher(lessons.get(0).getTeacher()).
		withGroups(lessons.get(0).getTimetable().getYear().getGroups()).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);
	
	lessonService.create(lesson);

	verify(lessonDao).create(lesson);
    }

    @Test
    void givenLessonWithBookedAudience_whenCreate_thenNotCreated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate().plusDays(1)).
		withEndDate(lessons.get(0).getDate().plusDays(7)).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate().plusDays(1)).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(lessons.get(0).getLessonTime()).
		withSubject(lessons.get(0).getTimetable().getYear().getSubjects().get(0)).
		withAudience(lessons.get(0).getAudience()).
		withGroups(lessons.get(0).getTimetable().getYear().getGroups()).
		withTeacher(new Teacher.TeacherBuilder().withId(10).built()).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);

	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }

    @Test
    void givenLessonWithSubjectNotAddedToYear_whenCreate_thenNotCreated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate().plusDays(1)).
		withEndDate(lessons.get(0).getDate().plusDays(7)).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate().plusDays(1)).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(3).built()).
		withSubject(new Subject.SubjectBuilder().withId(10).built()).
		withAudience(lessons.get(0).getAudience()).
		withTeacher(lessons.get(0).getTeacher()).
		withGroups(lessons.get(0).getTimetable().getYear().getGroups()).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);

	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }

    @Test
    void givenLessonWithBookedTeacher_whenCreate_thenNotCreated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate().plusDays(1)).
		withEndDate(lessons.get(0).getDate().plusDays(7)).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate().plusDays(1)).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(lessons.get(0).getLessonTime()).
		withSubject(lessons.get(0).getSubject()).
		withAudience(new Audience.AudienceBuilder().withId(10).built()).
		withTeacher(lessons.get(0).getTeacher()).
		withGroups(lessons.get(0).getTimetable().getYear().getGroups()).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);
	
	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }

    @Test
    void givenLessonWithGroupNotAddedToYear_whenCreate_thenNotCreated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate().plusDays(1)).
		withEndDate(lessons.get(0).getDate().plusDays(7)).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate().plusDays(1)).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(3).built()).
		withSubject(new Subject.SubjectBuilder().withId(10).built()).
		withAudience(lessons.get(0).getAudience()).
		withTeacher(lessons.get(0).getTeacher()).
		withGroups( Arrays.asList(new Group.GroupBuilder().withId(10).built())).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);
	
	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }

    @Test
    void givenLessonWithAudienceCapacityLessThenStuentsAmount_whenCreate_thenNotCreated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate().plusDays(1)).
		withEndDate(lessons.get(0).getDate().plusDays(7)).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate().plusDays(1)).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(3).built()).
		withSubject(lessons.get(0).getSubject()).
		withAudience(new Audience.AudienceBuilder().withId(10).withCapacity(1).built()).
		withTeacher(lessons.get(0).getTeacher()).
		withGroups(lessons.get(0).getTimetable().getYear().getGroups()).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);
	
	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }
    
    @Test
    void givenLessonWhithTeacherOnVocation_whenCreate_thenNotCreated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate()).
		withEndDate(lessons.get(0).getDate()).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate().plusDays(1)).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(3).built()).
		withSubject(lessons.get(0).getSubject()).
		withAudience(lessons.get(0).getAudience()).
		withTeacher(lessons.get(0).getTeacher()).
		withGroups(lessons.get(0).getTimetable().getYear().getGroups()).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);
	
	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }
    
    @Test
    void givenLessonWhithDateThatMatchToHoliday_whenCreate_thenNotCreated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate().plusDays(1)).
		withEndDate(lessons.get(0).getDate().plusDays(7)).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate()).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(lessons.get(0).getDate()).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(3).built()).
		withSubject(lessons.get(0).getSubject()).
		withAudience(lessons.get(0).getAudience()).
		withTeacher(lessons.get(0).getTeacher()).
		withGroups(lessons.get(0).getTimetable().getYear().getGroups()).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);
	
	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }
    
    @Test
    void givenLessonWhithDateThatMatchToWeekend_whenCreate_thenNotCreated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate().plusDays(5)).
		withEndDate(lessons.get(0).getDate().plusDays(10)).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate().plusDays(4)).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(lessons.get(0).getDate().plusDays(1)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(3).built()).
		withSubject(lessons.get(0).getSubject()).
		withAudience(lessons.get(0).getAudience()).
		withTeacher(lessons.get(0).getTeacher()).
		withGroups(lessons.get(0).getTimetable().getYear().getGroups()).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);
	
	lessonService.create(lesson);

	verify(lessonDao, never()).create(lesson);
    }
    
    @Test
    void givenLessonWhithAllCorrectFields_whenUpdate_thenUpdated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate().plusDays(1)).
		withEndDate(lessons.get(0).getDate().plusDays(7)).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate().plusDays(1)).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(3).built()).
		withSubject(lessons.get(0).getSubject()).
		withAudience(lessons.get(0).getAudience()).
		withTeacher(lessons.get(0).getTeacher()).
		withGroups(lessons.get(0).getTimetable().getYear().getGroups()).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);
	
	lessonService.update(lesson);

	verify(lessonDao).update(lesson);
    }

    @Test
    void givenLessonWithBookedAudience_whenUpdate_thenNotUpdated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate().plusDays(1)).
		withEndDate(lessons.get(0).getDate().plusDays(7)).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate().plusDays(1)).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(lessons.get(0).getLessonTime()).
		withSubject(lessons.get(0).getTimetable().getYear().getSubjects().get(0)).
		withAudience(lessons.get(0).getAudience()).
		withGroups(lessons.get(0).getTimetable().getYear().getGroups()).
		withTeacher(new Teacher.TeacherBuilder().withId(10).built()).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);

	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }

    @Test
    void givenLessonWithSubjectNotAddedToYear_whenUpdate_thenNotUpdated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate().plusDays(1)).
		withEndDate(lessons.get(0).getDate().plusDays(7)).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate().plusDays(1)).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(3).built()).
		withSubject(new Subject.SubjectBuilder().withId(10).built()).
		withAudience(lessons.get(0).getAudience()).
		withTeacher(lessons.get(0).getTeacher()).
		withGroups(lessons.get(0).getTimetable().getYear().getGroups()).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);

	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }

    @Test
    void givenLessonWithBookedTeacher_whenUpdate_thenNotUpdated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate().plusDays(1)).
		withEndDate(lessons.get(0).getDate().plusDays(7)).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate().plusDays(1)).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(lessons.get(0).getLessonTime()).
		withSubject(lessons.get(0).getSubject()).
		withAudience(new Audience.AudienceBuilder().withId(10).built()).
		withTeacher(lessons.get(0).getTeacher()).
		withGroups(lessons.get(0).getTimetable().getYear().getGroups()).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);
	
	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }

    @Test
    void givenLessonWithGroupNotAddedToYear_whenUpdate_thenNotUpdated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate().plusDays(1)).
		withEndDate(lessons.get(0).getDate().plusDays(7)).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate().plusDays(1)).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(3).built()).
		withSubject(new Subject.SubjectBuilder().withId(10).built()).
		withAudience(lessons.get(0).getAudience()).
		withTeacher(lessons.get(0).getTeacher()).
		withGroups( Arrays.asList(new Group.GroupBuilder().withId(10).built())).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);
	
	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }

    @Test
    void givenLessonWithAudienceCapacityLessThenStuentsAmount_whenUpdate_thenNotUpdated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate().plusDays(1)).
		withEndDate(lessons.get(0).getDate().plusDays(7)).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate().plusDays(1)).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(3).built()).
		withSubject(lessons.get(0).getSubject()).
		withAudience(new Audience.AudienceBuilder().withId(10).withCapacity(1).built()).
		withTeacher(lessons.get(0).getTeacher()).
		withGroups(lessons.get(0).getTimetable().getYear().getGroups()).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);

	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }
    
    @Test
    void givenLessonWhithTeacherOnVocation_whenUpdate_thenNotUpdated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate()).
		withEndDate(lessons.get(0).getDate()).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate().plusDays(1)).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(3).built()).
		withSubject(lessons.get(0).getSubject()).
		withAudience(lessons.get(0).getAudience()).
		withTeacher(lessons.get(0).getTeacher()).
		withGroups(lessons.get(0).getTimetable().getYear().getGroups()).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);
	
	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }
    
    @Test
    void givenLessonWhithDateThatMatchToHoliday_whenUpdate_thenNotUpdated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate().plusDays(1)).
		withEndDate(lessons.get(0).getDate().plusDays(7)).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate()).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(3).built()).
		withSubject(lessons.get(0).getSubject()).
		withAudience(lessons.get(0).getAudience()).
		withTeacher(lessons.get(0).getTeacher()).
		withGroups(lessons.get(0).getTimetable().getYear().getGroups()).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);
	
	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }
    
    @Test
    void givenLessonWhithDateThatMatchToWeekend_whenUpdate_thenNotUpdated() {
	List<Lesson> lessons = createLessons();
	List<Vocation> vocations = new ArrayList<>();
	Vocation vocation = new Vocation.VocationBuilder().withStartDate(lessons.get(0).getDate().plusDays(5)).
		withEndDate(lessons.get(0).getDate().plusDays(10)).withTeacher(lessons.get(0).getTeacher()).built();
	vocations.add(vocation);
	List<Holiday> holidays = new ArrayList<>(); 
	Holiday holiday =  new Holiday.HolidayBuilder().withDate(lessons.get(0).getDate().plusDays(4)).built();
	holidays.add(holiday);
	Lesson lesson = new Lesson.LessonBuilder().
		withId(1).
		withDate(lessons.get(0).getDate().plusDays(1)).
		withLessonTime(new LessonTime.LessonTimeBuilder().withId(3).built()).
		withSubject(lessons.get(0).getSubject()).
		withAudience(lessons.get(0).getAudience()).
		withTeacher(lessons.get(0).getTeacher()).
		withGroups(lessons.get(0).getTimetable().getYear().getGroups()).
		withTimetable(lessons.get(0).getTimetable()).
		built();
	when(lessonDao.findByDate(lesson)).thenReturn(lessons);
	when(vocationDao.findByTeacherId(lesson.getTeacher().getId())).thenReturn(vocations);
	when(holidayDao.findAll()).thenReturn(holidays);
	
	lessonService.update(lesson);

	verify(lessonDao, never()).update(lesson);
    }
    
    private List<Lesson> createLessons() {
	Student student1 = new Student.StudentBuilder().withId(1).built();
	Student student2 = new Student.StudentBuilder().withId(2).built();
	Student student3 = new Student.StudentBuilder().withId(3).built();
	Student student4 = new Student.StudentBuilder().withId(4).built();
	Subject subject1 = new Subject.SubjectBuilder().withId(1).withName("subject1").built();
	Subject subject2 = new Subject.SubjectBuilder().withId(2).withName("subject2").built();
	Teacher teacher1 = new Teacher.TeacherBuilder().withId(1).built();
	Teacher teacher2 = new Teacher.TeacherBuilder().withId(2).built();
	Speciality speciality = new Speciality.SpecialityBuilder().withId(1).withName("speciality").built();
	LessonsTimeSchedule lessonsTimeSchedule = new LessonsTimeSchedule.LessonsTimescheduleBuilder().withId(1).built();
	Group group1 = new Group.GroupBuilder().
		withId(1).
		withName("group1").
		withStudents(Arrays.asList(student1, student2)).
		built();
	Group group2 = new Group.GroupBuilder().
		withId(2).
		withName("group2").withStudents(Arrays.asList(student3, student4)).
		built();	
	Year year = new Year.YearBuilder().
		withId(1).
		withName("year").
		withGroups(Arrays.asList(group1, group2)).
		withSubjects(Arrays.asList(subject1, subject2)).
		withSpeciality(speciality).
		built();	
	Timetable timetable = new Timetable.TimetableBuilder().
		withId(1).
		withName("timetable").
		withYear(year).
		built();
	LessonTime lessonTime1 = new LessonTime.LessonTimeBuilder().withId(1).
		withOrderNumber("order number 1").
		withStartTime(LocalTime.of(8, 45)).withEndTime(LocalTime.of(9, 30)).
		withLessonsTimeSchedule(lessonsTimeSchedule).
		built();
	LessonTime lessonTime2 = new LessonTime.LessonTimeBuilder().
		withId(1).
		withOrderNumber("order number 2").
		withStartTime(LocalTime.of(9, 45)).withEndTime(LocalTime.of(10, 30)).
		withLessonsTimeSchedule(lessonsTimeSchedule).
		built();
	Audience audience = new Audience.AudienceBuilder().
		withId(1).
		withNumber("number 1").
		withBuilding(new Building.BuildingBuilder().withId(1).built()).withCapacity(4).
		withDescription("description").
		built();	
	Lesson lesson1 = new Lesson.LessonBuilder().
		withId(1).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(lessonTime1).
		withSubject(subject1).
		withAudience(audience).
		withTeacher(teacher1).
		withGroups(Arrays.asList(group1, group2)).
		withTimetable(timetable).
		built();
	Lesson lesson2 = new Lesson.LessonBuilder().
		withId(2).
		withDate(LocalDate.of(2021, 01, 01)).
		withLessonTime(lessonTime2).
		withSubject(subject2).
		withTeacher(teacher2).
		withGroups(Arrays.asList(group1, group2)).
		withTimetable(timetable).
		built();
	return new ArrayList<Lesson>(Arrays.asList(lesson1, lesson2));
    }

}
