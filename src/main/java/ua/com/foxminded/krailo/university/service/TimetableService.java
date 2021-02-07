package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.TimetableDao;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Timetable;
import ua.com.foxminded.krailo.university.model.Year;

@Service
public class TimetableService {

    private TimetableDao timetableDao;
    private LessonDao lessonDao;

    public TimetableService(TimetableDao timetableDao, LessonDao lessonDao) {
	this.timetableDao = timetableDao;
	this.lessonDao = lessonDao;
    }

    public void create(Timetable timetable) {
	timetableDao.create(timetable);
    }

    public Timetable getById(int timetableId) {
	Timetable timetable = timetableDao.findById(timetableId);
	addLessonsToTimetable(timetable);
	return timetable;
    }

    public List<Timetable> getAll() {
	List<Timetable> timetables = timetableDao.findAll();
	for (Timetable timetable : timetables) {
	    addLessonsToTimetable(timetable);
	}
	return timetables;
    }

    public void update(Timetable timetable) {
	checkYear(timetable.getYear());
	timetableDao.update(timetable);
    }

    public void deleteById(int id) {
	timetableDao.deleteById(id);
    }

    private void checkYear(Year year) {
	List<Lesson> lessons = lessonDao.findAll();
	if (lessons.stream().map(l -> l.getSubject()).filter(s -> !year.getSubjects().contains(s)).count() > 0) {
	    return;
	}
    }

    private void addLessonsToTimetable(Timetable timetable) {
	timetable.setLessons(lessonDao.findByTimetableId(timetable.getId()));
    }

}
