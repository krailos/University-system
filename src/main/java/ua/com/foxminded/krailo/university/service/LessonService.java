package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.VocationDao;
import ua.com.foxminded.krailo.university.exception.AudienceCapacityNotEnoughException;
import ua.com.foxminded.krailo.university.exception.AudienceNotFreeException;
import ua.com.foxminded.krailo.university.exception.LessonDateOnHolidayException;
import ua.com.foxminded.krailo.university.exception.LessonDateOnWeekendException;
import ua.com.foxminded.krailo.university.exception.LessonGroupNotFreeException;
import ua.com.foxminded.krailo.university.exception.ServiceException;
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
	Optional<Lesson> existingLesson = lessonDao.findById(id);
	if (existingLesson.isPresent()) {
	    return existingLesson.get();
	} else {
	    throw new ServiceException(format("Lesson with id=%s not exist", id));
	}
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
	log.debug("is teacher free tacherId={}", lesson.getTeacher().getId());
	if (lessonDao.findByDateAndTeacherIdAndLessonTimeId(lesson.getDate(), lesson.getTeacher().getId(),
		lesson.getLessonTime().getId()).isPresent()) {
	    throw new TeacherNotFreeException(format("Teacher not free teacherId=%s", lesson.getTeacher().getId()));
	}
	log.debug("teacher is free teacherId={}", lesson.getTeacher().getId());
    }

    private void checkAudienceBeFree(Lesson lesson) {
	log.debug("is audience free audienceId={}", lesson.getAudience().getId());
	if (lessonDao.findByDateAndAudienceIdAndLessonTimeId(lesson.getDate(), lesson.getAudience().getId(),
		lesson.getLessonTime().getId()).isPresent()) {
	    throw new AudienceNotFreeException(format("Audience not free audienceId=%s", lesson.getAudience().getId()));
	}
	log.debug("audience is free audienceId={}", lesson.getAudience().getId());
    }

    private void checkEnoughAudienceCapacity(Lesson lesson) {
	log.debug("is enought audience capacity={}", lesson.getAudience().getCapacity());
	if (lesson.getAudience().getCapacity() <= lesson.getGroups().stream().map(Group::getStudents)
		.mapToInt(List::size).sum()) {
	    throw new AudienceCapacityNotEnoughException(
		    format("audience with capacity=%s is not big enough", lesson.getAudience().getCapacity()));
	}
	log.debug("audience capacity is big enough");
    }

    private void checkTeacherBeOnVocation(Lesson lesson) {
	log.debug("is techer on vocation");
	if (vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()).isPresent()) {
	    throw new TeacherOnVocationException(
		    format("teacher with id=%s is on vocation", lesson.getTeacher().getId()));
	}
	log.debug("tecaher is not on vocation");
    }

    private void checkLessonDateBeOutoffHoliday(Lesson lesson) {
	log.debug("is lesson date holliday");
	if (holidayDao.findByDate(lesson.getDate()).isPresent()) {
	    throw new LessonDateOnHolidayException(format("lesson date=%s is holiday", lesson.getDate()));
	}
	log.debug("lesson date is not holiday");
    }

    private void checkLessonDateBeWeekend(Lesson lesson) {
	log.debug("is lesson date weekday");
	if (lesson.getDate().getDayOfWeek() == DayOfWeek.SATURDAY
		|| lesson.getDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
	    throw new LessonDateOnWeekendException(
		    format("lesson date is weekend=%s", lesson.getDate().getDayOfWeek()));
	}
	log.debug("lesson date is not weekend");
    }

    private void checkTeacherBeTeachLessonSubject(Lesson lesson) {
	log.debug("is teacher teches lesson subject");
	if (!lesson.getTeacher().getSubjects().contains(lesson.getSubject())) {
	    throw new TeacherNotTeachLessonException("teacher dosn't teach lesson's subject");
	}
	log.debug("teacher teches lesson subject");
    }

    private void checkLessonGroupsBeFree(Lesson lesson) {
	log.debug("is lessons group free");
	if (lesson.getGroups().stream().anyMatch(g -> lessonDao
		.findByDateAndLessonTimeIdAndGroupId(lesson.getDate(), lesson.getLessonTime().getId(), g.getId())
		.isPresent())) {
	    throw new LessonGroupNotFreeException("lessons groups are not free");
	}
	log.debug("lessons groups are free");
    }

}
