package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.time.DayOfWeek;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.VocationDao;
import ua.com.foxminded.krailo.university.exception.AudienceCapacityNotEnoughException;
import ua.com.foxminded.krailo.university.exception.AudienceNotFreeException;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.LessonDateOnHolidayException;
import ua.com.foxminded.krailo.university.exception.LessonDateOnWeekendException;
import ua.com.foxminded.krailo.university.exception.LessonGroupNotFreeException;
import ua.com.foxminded.krailo.university.exception.TeacherNotFreeException;
import ua.com.foxminded.krailo.university.exception.TeacherNotTeachLessonException;
import ua.com.foxminded.krailo.university.exception.TeacherOnVocationException;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;

@Service
public class LessonService {

    private static final Logger log = LoggerFactory.getLogger(LessonService.class);

    private LessonDao lessonDao;
    private VocationDao vocationDao;
    private HolidayDao holidayDao;

    public LessonService(LessonDao lessonDao, VocationDao vocationDao, HolidayDao holidayDao) {
	this.lessonDao = lessonDao;
	this.vocationDao = vocationDao;
	this.holidayDao = holidayDao;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void create(Lesson lesson) {
	log.debug("create lesson");
	checkTeacherBeFree(lesson);
	checkEnoughAudienceCapacity(lesson);
	checkTeacherBeOnVocation(lesson);
	checkAudienceBeFree(lesson);
	checkLessonDateBeOutoffHoliday(lesson);
	checkLessonDateBeWeekend(lesson);
	checkTeacherBeTeachLessonSubject(lesson);
	checkLessonGroupsBeFree(lesson);
	lessonDao.create(lesson);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(Lesson lesson) {
	log.debug("update lesson");
	checkTeacherBeFree(lesson);
	checkEnoughAudienceCapacity(lesson);
	checkTeacherBeOnVocation(lesson);
	checkAudienceBeFree(lesson);
	checkLessonDateBeOutoffHoliday(lesson);
	checkLessonDateBeWeekend(lesson);
	checkTeacherBeTeachLessonSubject(lesson);
	checkLessonGroupsBeFree(lesson);
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

    private void checkTeacherBeFree(Lesson lesson) {
	if (lessonDao.findByDateAndTeacherIdAndLessonTimeId(lesson.getDate(), lesson.getTeacher().getId(),
		lesson.getLessonTime().getId()).isPresent()) {
	    throw new TeacherNotFreeException("Teacher not free, teacherId=%s" + lesson.getTeacher().getId());
	}
    }

    private void checkAudienceBeFree(Lesson lesson) {
	if (lessonDao.findByDateAndAudienceIdAndLessonTimeId(lesson.getDate(), lesson.getAudience().getId(),
		lesson.getLessonTime().getId()).isPresent()) {
	    throw new AudienceNotFreeException("Audience not free, audienceId=%s" + lesson.getAudience().getId());
	}
    }

    private void checkEnoughAudienceCapacity(Lesson lesson) {
	if (lesson.getAudience().getCapacity() <= lesson.getGroups().stream().map(Group::getStudents)
		.mapToInt(List::size).sum()) {
	    throw new AudienceCapacityNotEnoughException(
		    "audience capacity not big enough, audience capacity=%s " + lesson.getAudience().getCapacity());
	}
    }

    private void checkTeacherBeOnVocation(Lesson lesson) {
	if (vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()).isPresent()) {
	    throw new TeacherOnVocationException("teacher on vocation, teacherId=%s" + lesson.getTeacher().getId());
	}
    }

    private void checkLessonDateBeOutoffHoliday(Lesson lesson) {
	if (holidayDao.findByDate(lesson.getDate()).isPresent()) {
	    throw new LessonDateOnHolidayException(format("lesson date=%s is holiday", lesson.getDate()));
	}
    }

    private void checkLessonDateBeWeekend(Lesson lesson) {
	if (lesson.getDate().getDayOfWeek() == DayOfWeek.SATURDAY
		|| lesson.getDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
	    throw new LessonDateOnWeekendException("lesson date is weekend=%s" + lesson.getDate().getDayOfWeek());
	}
    }

    private void checkTeacherBeTeachLessonSubject(Lesson lesson) {
	if (!lesson.getTeacher().getSubjects().contains(lesson.getSubject())) {
	    throw new TeacherNotTeachLessonException("teacher dosn't teach lesson's subject");
	}
    }

    private void checkLessonGroupsBeFree(Lesson lesson) {
	if (lesson.getGroups().stream().anyMatch(g -> lessonDao
		.findByDateAndLessonTimeIdAndGroupId(lesson.getDate(), lesson.getLessonTime().getId(), g.getId())
		.isPresent())) {
	    throw new LessonGroupNotFreeException("lessons groups are not free");
	}
    }

}
