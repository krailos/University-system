package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.TimetableDao;
import ua.com.foxminded.krailo.university.dao.YearDao;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Timetable;
import ua.com.foxminded.krailo.university.model.Year;

@Service
public class TimetableService {

    private TimetableDao timetableDao;
    private LessonDao lessonDao;
    private YearDao yearDao;

    public TimetableService(TimetableDao timetableDao, LessonDao lessonDao, YearDao yearDao) {
	this.timetableDao = timetableDao;
	this.lessonDao = lessonDao;
	this.yearDao = yearDao;
    }

    public void add(String name, int yearId) {
	Timetable timetable = new Timetable();
	timetable.setName(name);
	timetable.setYear(yearDao.findById(yearId));
	timetableDao.create(timetable);
    }

    public Timetable getById(int timetableId) {
	Timetable timetable = timetableDao.findById(timetableId);
	timetable.setLessons(lessonDao.findByTimetableId(timetableId));
	return timetable;
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
	    throw new RuntimeException("some subjects from lessons don't register in this year");
	}
    }

}
