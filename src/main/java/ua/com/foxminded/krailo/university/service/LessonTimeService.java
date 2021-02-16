package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.LessonTimeDao;
import ua.com.foxminded.krailo.university.model.LessonTime;

@Service
public class LessonTimeService {

    private LessonTimeDao lessonTimeDao;

    public LessonTimeService(LessonTimeDao lessonTimeDao) {
	this.lessonTimeDao = lessonTimeDao;
    }

    public void create(LessonTime lessonTime) {
	lessonTimeDao.create(lessonTime);
    }

    public void update(LessonTime lessonTime) {
	lessonTimeDao.update(lessonTime);
    }

    public LessonTime getById(int id) {
	return lessonTimeDao.findById(id);
    }

    public List<LessonTime> getAll() {
	return lessonTimeDao.findAll();
    }

    public void delete(LessonTime lessonTime) {
	lessonTimeDao.deleteById(lessonTime.getId());
    }

}
