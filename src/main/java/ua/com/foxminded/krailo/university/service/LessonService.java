package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.VocationDao;
import ua.com.foxminded.krailo.university.exception.AudienceNotFreeException;
import ua.com.foxminded.krailo.university.exception.AudienceOverflowException;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.GroupNotFreeException;
import ua.com.foxminded.krailo.university.exception.LessonDateOnHolidayException;
import ua.com.foxminded.krailo.university.exception.LessonDateOnWeekendException;
import ua.com.foxminded.krailo.university.exception.TeacherNotFreeException;
import ua.com.foxminded.krailo.university.exception.TeacherNotTeachLessonException;
import ua.com.foxminded.krailo.university.exception.TeacherOnVocationException;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Teacher;

@Transactional
@Service
public class LessonService {

    private static final Logger log = LoggerFactory.getLogger(LessonService.class);

    private LessonDao lessonDao;
    private VocationDao vocationDao;
    private HolidayDao holidayDao;
    private GroupDao groupDao;

    public LessonService(LessonDao lessonDao, VocationDao vocationDao, HolidayDao holidayDao, GroupDao groupDao) {
	this.lessonDao = lessonDao;
	this.vocationDao = vocationDao;
	this.holidayDao = holidayDao;
	this.groupDao = groupDao;
    }

    public void create(Lesson lesson) {
	log.debug("create lesson={}", lesson);
	checkTeacherIsFree(lesson);
	checkOverflowAudienceCapacity(lesson);
	checkTeacherIsOnVocation(lesson);
	checkAudienceIsFree(lesson);
	checkLessonDateIsOutoffHoliday(lesson);
	checkLessonDateIsWeekend(lesson);
	checkTeacherTeachesLessonSubject(lesson);
	checkLessonGroupsAreFree(lesson);
	lessonDao.create(lesson);
    }

    public void update(Lesson lesson) {
	log.debug("update lesson={}", lesson);
	checkTeacherIsFree(lesson);
	checkOverflowAudienceCapacity(lesson);
	checkTeacherIsOnVocation(lesson);
	checkAudienceIsFree(lesson);
	checkLessonDateIsOutoffHoliday(lesson);
	checkLessonDateIsWeekend(lesson);
	checkTeacherTeachesLessonSubject(lesson);
	checkLessonGroupsAreFree(lesson);
	lessonDao.update(lesson);
    }

    public Lesson getById(int id) {
	log.debug("Get lesson by id={}", id);
	return lessonDao.getById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Lesson whith id=%s not exist", id)));
    }

    public List<Lesson> getAll() {
	log.debug("get all lessons");
	return lessonDao.getAll();
    }

    public void delete(Lesson lesson) {
	log.debug("delete lesson={}", lesson);
	lessonDao.delete(lesson);
    }

    public int getQuantity() {
	log.debug("get lessons quantity");
	return lessonDao.count();
    }

    public Page<Lesson> getSelectedPage(Pageable pageable) {
	log.debug("get lessons by page");
	return new PageImpl<>(lessonDao.getByPage(pageable), pageable, lessonDao.count());
    }

    public List<Lesson> getLessonsForTeacherByDate(Teacher teacher, LocalDate date) {
	log.debug("get lessons for teacher={} by date={}", teacher, date);
	return lessonDao.getByTeacherAndDate(teacher, date);
    }

    public List<Lesson> getLessonsForTeacherByPeriod(Teacher teacher, LocalDate startDate, LocalDate finishDate) {
	log.debug("get timetable for teacher={} by month", teacher);
	return lessonDao.getByTeacherBetweenDates(teacher, startDate, finishDate);
    }

    public List<Lesson> getLessonsForStudentByDate(Student student, LocalDate date) {
	log.debug("get timetable for student={} by date={}", student, date);
	return lessonDao.getByGroupAndDate(groupDao.getById(student.getGroup().getId()).get(), date);

    }

    public List<Lesson> getLessonsForStudentByPeriod(Student student, LocalDate startDate, LocalDate finishDate) {
	log.debug("get timetable for student={} by month", student);
	return lessonDao.getByGroupBetweenDates(groupDao.getById(student.getGroup().getId()).get(), startDate,
		finishDate);

    }

    public void substituteTeacher(Teacher oldTeacher, Teacher newTeacher, LocalDate startDate, LocalDate finishDate) {
	lessonDao.getByTeacherBetweenDates(oldTeacher, startDate, finishDate).stream().peek(l -> {
	    checkTeacherIsFree(l);
	    checkTeacherTeachesLessonSubject(l);
	    l.setTeacher(newTeacher);
	}).forEach(lessonDao::update);
    }

    private void checkTeacherIsFree(Lesson lesson) {
	Optional<Lesson> existingLesson = lessonDao.getByDateAndTeacherAndLessonTime(lesson.getDate(),
		lesson.getTeacher(), lesson.getLessonTime());
	if (existingLesson.filter(l -> l.getId() != lesson.getId()).isPresent()) {
	    throw new TeacherNotFreeException("Teacher not free, teacherId=" + lesson.getTeacher().getId());
	}
    }

    private void checkAudienceIsFree(Lesson lesson) {
	Optional<Lesson> existingLeson = lessonDao.getByDateAndAudienceAndLessonTime(lesson.getDate(),
		lesson.getAudience(), lesson.getLessonTime());
	if (existingLeson.filter(l -> l.getId() != lesson.getId()).isPresent()) {
	    throw new AudienceNotFreeException("Audience not free, audienceId=" + lesson.getAudience().getId());
	}
    }

    private void checkOverflowAudienceCapacity(Lesson lesson) {
	if (lesson.getAudience().getCapacity() <= lesson.getGroups().stream().map(Group::getStudents)
		.mapToInt(List::size).sum()) {
	    throw new AudienceOverflowException(
		    "audience is owerflowed, audience capacity=" + lesson.getAudience().getCapacity());
	}
    }

    private void checkTeacherIsOnVocation(Lesson lesson) {
	if (vocationDao.getByTeacherAndDate(lesson.getTeacher(), lesson.getDate()).isPresent()) {
	    throw new TeacherOnVocationException("teacher on vocation, teacherId=" + lesson.getTeacher().getId());
	}
    }

    private void checkLessonDateIsOutoffHoliday(Lesson lesson) {
	if (holidayDao.getByDate(lesson.getDate()).isPresent()) {
	    throw new LessonDateOnHolidayException(format("lesson date=%s is holiday", lesson.getDate()));
	}
    }

    private void checkLessonDateIsWeekend(Lesson lesson) {
	if (lesson.getDate().getDayOfWeek() == DayOfWeek.SATURDAY
		|| lesson.getDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
	    throw new LessonDateOnWeekendException("lesson date is weekend=" + lesson.getDate().getDayOfWeek());
	}
    }

    private void checkTeacherTeachesLessonSubject(Lesson lesson) {
	if (lesson.getTeacher().getSubjects().stream().noneMatch(s -> s.getId() == lesson.getSubject().getId())) {
	    throw new TeacherNotTeachLessonException("teacher dosn't teach lesson's subject");
	}
    }

    private void checkLessonGroupsAreFree(Lesson lesson) {
	if (lesson.getGroups().stream()
		.map(g -> lessonDao.getByDateAndLessonTimeAndGroup(lesson.getDate(), lesson.getLessonTime(), g))
		.anyMatch(o -> o.filter(l -> l.getId() != lesson.getId()).isPresent())) {
	    throw new GroupNotFreeException("lesson groups are not free");
	}
    }

}
