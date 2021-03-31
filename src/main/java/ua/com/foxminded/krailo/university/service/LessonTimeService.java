package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.LessonTimeDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.LessonTimeNotFreeException;
import ua.com.foxminded.krailo.university.model.LessonTime;

@Service
public class LessonTimeService {

    private static final Logger log = LoggerFactory.getLogger(LessonTimeService.class);

    private LessonTimeDao lessonTimeDao;

    public LessonTimeService(LessonTimeDao lessonTimeDao) {
	this.lessonTimeDao = lessonTimeDao;
    }

    public void create(LessonTime lessonTime) {
	log.debug("Create lessonTime={}", lessonTime);
	checkLessonTimeBeFree(lessonTime);
	lessonTimeDao.create(lessonTime);
    }

    public void update(LessonTime lessonTime) {
	log.debug("Update lessonTime={}", lessonTime);
	checkLessonTimeBeFree(lessonTime);
	lessonTimeDao.update(lessonTime);
    }

    public LessonTime getById(int id) {
	log.debug("Get lessonTime by id={}", id);
	return lessonTimeDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("LessonTime whith id=%s not exist", id)));
    }

    public List<LessonTime> getAll() {
	log.debug("Get all lessonTimes");
	return lessonTimeDao.findAll();
    }

    public void delete(LessonTime lessonTime) {
	log.debug("Delete lessonTime={}", lessonTime);
	lessonTimeDao.deleteById(lessonTime.getId());
    }

    private void checkLessonTimeBeFree(LessonTime lessonTime) {
	if (lessonTimeDao.findByStartOrEndLessonTime(lessonTime).isPresent()) {
	    throw new LessonTimeNotFreeException("lessonTime not free, lessonTime=" + lessonTime);
	}
    }

}