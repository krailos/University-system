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
import ua.com.foxminded.krailo.university.exception.ServiceException;
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
	isTeacherFree(lesson);
	isEnoughAudienceCapacity(lesson);
	isTeacherOnVocation(lesson);
	isAudienceFree(lesson);
	isLessonDateOutoffHoliday(lesson);
	isLessonDateWeekday(lesson);
	isTeacherTeachesLessonSubject(lesson);
	isLessonGroupsFree(lesson);
	lessonDao.create(lesson);
    }

    public void update(Lesson lesson) {
	log.debug("update lesson");
	isTeacherFree(lesson);
	isEnoughAudienceCapacity(lesson);
	isTeacherOnVocation(lesson);
	isAudienceFree(lesson);
	isLessonDateOutoffHoliday(lesson);
	isLessonDateWeekday(lesson);
	isTeacherTeachesLessonSubject(lesson);
	isLessonGroupsFree(lesson);
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

    private void isTeacherFree(Lesson lesson) {
	log.debug("is teacher free tacherId={}", lesson.getTeacher().getId());
	if (lessonDao.findByDateAndTeacherAndLessonTime(lesson).isPresent()) {
	    throw new ServiceException(format("Teacher not free teacherId=%s", lesson.getTeacher().getId()));
	}
	log.debug("teacher is free teacherId={}", lesson.getTeacher().getId());
    }

    private void isAudienceFree(Lesson lesson) {
	log.debug("is audience free audienceIdId={}", lesson.getAudience().getId());
	if (lessonDao.findByDateAndAudienceAndLessonTime(lesson).isPresent()) {
	    throw new ServiceException(format("Audience not free audienceId=%s", lesson.getAudience().getId()));
	}
	log.debug("audience is free audienceId={}", lesson.getAudience().getId());
    }

    private void isEnoughAudienceCapacity(Lesson lesson) {
	if (lesson.getAudience().getCapacity() <= lesson.getGroups().stream().map(Group::getStudents)
		.mapToInt(List::size).sum()) {
	    throw new ServiceException(
		    format("audience with capacity=%s is not big enought", lesson.getAudience().getCapacity()));
	}
	log.debug("audience capacity is big enought");
    }

    private void isTeacherOnVocation(Lesson lesson) {
	log.debug("is techer on vocation");
	if (vocationDao.findByTeacherIdAndDate(lesson.getTeacher().getId(), lesson.getDate()).isPresent()) {
	    throw new ServiceException(format("teacher with id=%s is on vocation", lesson.getTeacher().getId()));
	}
	log.debug("tecaher is not on vocation");
    }

    private void isLessonDateOutoffHoliday(Lesson lesson) {
	log.debug("is lesson date holliday");
	if (holidayDao.findByDate(lesson.getDate()).isPresent()) {
	    throw new ServiceException(format("lesson date=%s is holiday", lesson.getDate()));
	}
	log.debug("lesson date is not holiday");
    }

    private void isLessonDateWeekday(Lesson lesson) {
	log.debug("is lesson date weekday");
	if (lesson.getDate().getDayOfWeek() == DayOfWeek.SATURDAY
		|| lesson.getDate().getDayOfWeek() == DayOfWeek.SUNDAY) {
	    throw new ServiceException(format("lesson date is weekday=%s", lesson.getDate().getDayOfWeek()));
	}
	log.debug("lesson date is not weekday");
    }

    private void isTeacherTeachesLessonSubject(Lesson lesson) {
	log.debug("is teacher teches lesson subject");
	if (!lesson.getTeacher().getSubjects().contains(lesson.getSubject())) {
	    throw new ServiceException("teacher dosn't teach lesson's subject");
	}
	log.debug("teacher teches lesson subject");
    }

    private void isLessonGroupsFree(Lesson lesson) {
	log.debug("is lessons group free");
	if (lesson.getGroups().stream().anyMatch(g -> lessonDao
		.findByDateAndLessonTimeIdAndGroupId(lesson.getDate(), lesson.getLessonTime().getId(), g.getId())
		.isPresent())) {
	    throw new ServiceException("lessons groups are not free");
	}
	log.debug("lessons groups are free");
    }

}
