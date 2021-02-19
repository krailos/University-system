package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.LessonTimeScheduleDao;
import ua.com.foxminded.krailo.university.model.LessonsTimeSchedule;

@Service
public class LessonsTimeScheduleService {

    private LessonTimeScheduleDao lessonTimeScheduleDao;

    public LessonsTimeScheduleService(LessonTimeScheduleDao lessonTimeScheduleDao) {
	this.lessonTimeScheduleDao = lessonTimeScheduleDao;
    }

    public void create(LessonsTimeSchedule lessonsTimeSchedule) {
	lessonTimeScheduleDao.create(lessonsTimeSchedule);
    }

    public void update(LessonsTimeSchedule lessonsTimeSchedule) {
	lessonTimeScheduleDao.update(lessonsTimeSchedule);
    }

    public LessonsTimeSchedule getById(int id) {
	return lessonTimeScheduleDao.findById(id);
    }

    public List<LessonsTimeSchedule> getAll() {
	return lessonTimeScheduleDao.findAll();
    }

    public void delete(LessonsTimeSchedule lessonsTimeSchedule) {
	lessonTimeScheduleDao.deleteById(lessonsTimeSchedule.getId());
    }

}
