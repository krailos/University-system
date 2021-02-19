package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.TimetableDao;
import ua.com.foxminded.krailo.university.model.Timetable;

@Service
public class TimetableService {

    private TimetableDao timetableDao;

    public TimetableService(TimetableDao timetableDao) {
	this.timetableDao = timetableDao;
    }

    public void create(Timetable timetable) {
	timetableDao.create(timetable);
    }

    public void update(Timetable timetable) {
	timetableDao.update(timetable);
    }

    public Timetable getById(int id) {
	return timetableDao.findById(id);
    }

    public List<Timetable> getAll() {
	return timetableDao.findAll();
    }

    public void delete(Timetable timetable) {
	timetableDao.deleteById(timetable.getId());
    }

}
