package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.LessonTimeScheduleDao2;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.model.LessonsTimeSchedule;

@Service
public class LessonsTimeScheduleService {

    private static final Logger log = LoggerFactory.getLogger(LessonsTimeScheduleService.class);

    private LessonTimeScheduleDao2 lessonTimeScheduleDao;

    public LessonsTimeScheduleService(LessonTimeScheduleDao2 lessonTimeScheduleDao) {
	this.lessonTimeScheduleDao = lessonTimeScheduleDao;
    }

    public void create(LessonsTimeSchedule lessonsTimeSchedule) {
	log.debug("Create lessonsTimeSchedule={}", lessonsTimeSchedule);
	lessonTimeScheduleDao.create(lessonsTimeSchedule);
    }

    public void update(LessonsTimeSchedule lessonsTimeSchedule) {
	log.debug("Update lessonsTimeSchedule={}", lessonsTimeSchedule);
	lessonTimeScheduleDao.update(lessonsTimeSchedule);
    }

    public LessonsTimeSchedule getById(int id) {
	log.debug("Get lessonsTimeSchedule by id={}", id);
	return lessonTimeScheduleDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("LessonTimeSchedule whith id=%s not exist", id)));
    }

    public List<LessonsTimeSchedule> getAll() {
	log.debug("Get all lessonsTimeSchedules");
	return lessonTimeScheduleDao.findAll();
    }

    public void delete(LessonsTimeSchedule lessonsTimeSchedule) {
	log.debug("Delete lessonsTimeSchedule={}", lessonsTimeSchedule);
	lessonTimeScheduleDao.deleteById(lessonsTimeSchedule.getId());
    }

}