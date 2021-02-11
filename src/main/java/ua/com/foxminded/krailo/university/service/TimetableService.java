package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.TimetableDao;
import ua.com.foxminded.krailo.university.model.Timetable;

@Service
public class TimetableService {

    private TimetableDao timetableDao;
    private LessonDao lessonDao;

    public TimetableService(TimetableDao timetableDao, LessonDao lessonDao) {
	this.timetableDao = timetableDao;
	this.lessonDao = lessonDao;
    }

    public void create(Timetable timetable) {
	List<Timetable> timetables = timetableDao.findByYear(timetable);
	if (checkUnique(timetables, timetable)) {
	    timetableDao.create(timetable);
	}
    }

    public void update(Timetable timetable) {
	List<Timetable> timetables = timetableDao.findByYear(timetable);
	if (checkUnique(timetables, timetable)) {
	    timetableDao.update(timetable);
	}
    }

    public Timetable getById(int id) {
	Timetable timetable = timetableDao.findById(id);
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

    public void delete(Timetable timetable) {
	timetableDao.deleteById(timetable.getId());
    }

    private boolean checkUnique(List<Timetable> timetables, Timetable timetable) {
	return timetables.stream().map(Timetable::getName).noneMatch(n -> n.equals(timetable.getName()));
    }

    private void addLessonsToTimetable(Timetable timetable) {
	timetable.setLessons(lessonDao.findByTimetableId(timetable.getId()));
    }

}
