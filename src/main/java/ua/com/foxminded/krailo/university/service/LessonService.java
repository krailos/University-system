package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.TeacherDao;
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
import ua.com.foxminded.krailo.university.util.Paging;

@Service
public class LessonService {

    private static final Logger log = LoggerFactory.getLogger(LessonService.class);

    private LessonDao lessonDao;
    private VocationDao vocationDao;
    private HolidayDao holidayDao;
    private TeacherDao teacherDao;

    public LessonService(LessonDao lessonDao, VocationDao vocationDao, HolidayDao holidayDao, TeacherDao teacherDao) {
	this.lessonDao = lessonDao;
	this.vocationDao = vocationDao;
	this.holidayDao = holidayDao;
	this.teacherDao = teacherDao;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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
	return lessonDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Lesson whith id=%s not exist", id)));
    }

    public List<Lesson> getAll() {
	log.debug("get all lessons");
	return lessonDao.findAll();
    }

    public void delete(Lesson lesson) {
	log.debug("delete lesson={}", lesson);
	lessonDao.deleteById(lesson.getId());
    }

    public int getQuantity() {
	log.debug("get lessons quantity");
	return lessonDao.findQuantity();
    }

    public List<Lesson> getByPage(Paging paging) {
	log.debug("get lessons by page");
	return lessonDao.findWithLimit(paging.getPageSize(), paging.getOffset());
    }

    public List<Lesson> getLessonsForTeacherByDate(Teacher teacher, LocalDate date) {
	log.debug("get lessons for teacher={} by date={}", teacher, date);
	return lessonDao.findByTeacherAndDate(teacher, date);
    }

    public List<Lesson> getLessonsForTeacherByPeriod(Teacher teacher, LocalDate startDate, LocalDate finishDate) {
	log.debug("get timetable for teacher={} by month", teacher);
	return lessonDao.findByTeacherBetweenDates(teacher, startDate, finishDate);
    }

    public List<Lesson> getLessonsForStudentByDate(Student student, LocalDate date) {
	log.debug("get timetable for student={} by date={}", student, date);
	return lessonDao.findByStudentAndDate(student, date);

    }

    public List<Lesson> getLessonsForStudentByPeriod(Student student, LocalDate startDate, LocalDate finishDate) {
	log.debug("get timetable for student={} by month", student);
	return lessonDao.findByStudentBetweenDates(student, startDate, finishDate);

    }

//    public boolean isTeacherReplaced(int oldTeacherId, int newTecherId, LocalDate lessonDate) {
//	Teacher oldTeacher = teacherDao.findById(oldTeacherId)
//		.orElseThrow(() -> new EntityNotFoundException(format("Lesson whith id=%s not exist", oldTeacherId)));
//	Teacher newTeacher = teacherDao.findById(newTecherId)
//		.orElseThrow(() -> new EntityNotFoundException(format("Lesson whith id=%s not exist", newTecherId)));
//	List<Lesson> oldTeacherLessons = lessonDao.findByTeacherAndDate(oldTeacher, lessonDate);
//	return !oldTeacherLessons.stream()
//		.anyMatch(l -> lessonDao
//			.findByDateAndTeacherIdAndLessonTimeId(l.getDate(), newTeacher.getId(),
//				l.getLessonTime().getId())
//			.isPresent() || !newTeacher.getSubjects().contains(l.getSubject()));
//    }

    public List<Teacher> findTeachersForSubstitute(int substitutedTeacherId, LocalDate startDate,
	    LocalDate finishDate) {
	Teacher substitutedTeacher = teacherDao.findById(substitutedTeacherId).orElseThrow(
		() -> new EntityNotFoundException(format("Lesson whith id=%s not exist", substitutedTeacherId)));
	List<Lesson> substitutedLessons = lessonDao.findByTeacherBetweenDates(substitutedTeacher, startDate,
		finishDate);
	return teacherDao.findAll().stream()
		.filter(t -> substitutedLessons.stream()
			.allMatch(l -> !lessonDao.findByDateAndTeacherIdAndLessonTimeId(l.getDate(), t.getId(),
				l.getLessonTime().getId()).isPresent() && t.getSubjects().contains(l.getSubject())))
		.collect(Collectors.toList());
    }

    private void checkTeacherIsFree(Lesson lesson) {
	Optional<Lesson> existingLesson = lessonDao.findByDateAndTeacherIdAndLessonTimeId(lesson.getDate(),
		lesson.getTeacher().getId(), lesson.getLessonTime().getId());
	if (existingLesson.filter(l -> l.getId() != lesson.getId()).isPresent()) {
	    throw new TeacherNotFreeException("Teacher not free, teacherId=" + lesson.getTeacher().getId());
	}
    }

    private void checkAudienceIsFree(Lesson lesson) {
	Optional<Lesson> existingLeson = lessonDao.findByDateAndAudienceIdAndLessonTimeId(lesson.getDate(),
		lesson.getAudience().getId(), lesson.getLessonTime().getId());
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
	if (vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()).isPresent()) {
	    throw new TeacherOnVocationException("teacher on vocation, teacherId=" + lesson.getTeacher().getId());
	}
    }

    private void checkLessonDateIsOutoffHoliday(Lesson lesson) {
	if (holidayDao.findByDate(lesson.getDate()).isPresent()) {
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
	if (!lesson.getTeacher().getSubjects().contains(lesson.getSubject())) {
	    throw new TeacherNotTeachLessonException("teacher dosn't teach lesson's subject");
	}
    }

    private void checkLessonGroupsAreFree(Lesson lesson) {
	if (lesson.getGroups().stream()
		.map(g -> lessonDao.findByDateAndLessonTimeIdAndGroupId(lesson.getDate(),
			lesson.getLessonTime().getId(), g.getId()))
		.anyMatch(o -> o.filter(l -> l.getId() != lesson.getId()).isPresent())) {
	    throw new GroupNotFreeException("lesson groups are not free");
	}
    }

}
