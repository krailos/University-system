package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.LessonTimeDao;
import ua.com.foxminded.krailo.university.dao.LessonTimeScheduleDao;
import ua.com.foxminded.krailo.university.model.LessonsTimeSchedule;

@Service
public class LessonsTimeScheduleService {

    private LessonTimeScheduleDao lessonTimeScheduleDao;
    private LessonTimeDao lessonTimeDao;

    public LessonsTimeScheduleService(LessonTimeScheduleDao lessonTimeScheduleDao, LessonTimeDao lessonTimeDao) {
	this.lessonTimeScheduleDao = lessonTimeScheduleDao;
	this.lessonTimeDao = lessonTimeDao;
    }

    public void create(LessonsTimeSchedule lessonsTimeSchedule) {
	lessonTimeScheduleDao.create(lessonsTimeSchedule);
    }

    public void update(LessonsTimeSchedule lessonsTimeSchedule) {
	lessonTimeScheduleDao.update(lessonsTimeSchedule);
    }

    public LessonsTimeSchedule getById(int id) {
	LessonsTimeSchedule lessonsTimeSchedule = lessonTimeScheduleDao.findById(id);
	addLessonTimeToLessonsTimeSchedule(lessonsTimeSchedule);
	return lessonsTimeSchedule;
    }

    public List<LessonsTimeSchedule> getAll() {
	List<LessonsTimeSchedule> lessonsTimeSchedules = lessonTimeScheduleDao.findAll();
	for (LessonsTimeSchedule lessonsTimeSchedule : lessonsTimeSchedules) {
	    addLessonTimeToLessonsTimeSchedule(lessonsTimeSchedule);
	}
	return lessonsTimeSchedules;
    }

    public void delete(LessonsTimeSchedule lessonsTimeSchedule) {
	lessonTimeScheduleDao.deleteById(lessonsTimeSchedule.getId());
    }

    private void addLessonTimeToLessonsTimeSchedule(LessonsTimeSchedule lessonsTimeSchedule) {
	lessonsTimeSchedule.setLessonTimes(lessonTimeDao.findBylessonTimeScheduleId(lessonsTimeSchedule.getId()));
    }

}
