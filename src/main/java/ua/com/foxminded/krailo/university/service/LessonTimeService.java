package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.dao.interf.LessonTimeDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.LessonTimeNotFreeException;
import ua.com.foxminded.krailo.university.model.LessonTime;

@Transactional
@Service
public class LessonTimeService {

    private static final Logger log = LoggerFactory.getLogger(LessonTimeService.class);

    private LessonTimeDao lessonTimeDao;

    public LessonTimeService(LessonTimeDao lessonTimeDaoInt) {
	this.lessonTimeDao = lessonTimeDaoInt;
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
	return lessonTimeDao.getById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("LessonTime whith id=%s not exist", id)));
    }

    public List<LessonTime> getAll() {
	log.debug("Get all lessonTimes");
	return lessonTimeDao.getAll();
    }

    public void delete(LessonTime lessonTime) {
	log.debug("Delete lessonTime={}", lessonTime);
	lessonTimeDao.delete(lessonTime);
    }

    private void checkLessonTimeBeFree(LessonTime lessonTime) {
	Optional<LessonTime> existingLessonTime = lessonTimeDao.getByStartOrEndLessonTime(lessonTime);
	log.debug("existing audience={}", existingLessonTime);
	if (existingLessonTime.filter(a -> a.getId() != lessonTime.getId()).isPresent()) {
	    throw new LessonTimeNotFreeException("lessonTime not free, lessonTime=" + lessonTime);
	}
    }

}
