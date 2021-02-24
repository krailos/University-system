package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.VocationDao;
import ua.com.foxminded.krailo.university.model.Vocation;

@Service
public class VocationService {

    private VocationDao vocationDao;
    private LessonDao lessonDao;

    public VocationService(VocationDao vocationDao, LessonDao lessonDao) {
	this.vocationDao = vocationDao;
	this.lessonDao = lessonDao;
    }

    public void create(Vocation vocation) {
	if (isVocationPeriodFreeOfLessons(vocation) && isVocationsEndDateMoreThenStart(vocation)) {
	    vocationDao.create(vocation);
	}
    }

    public void update(Vocation vocation) {
	if (isVocationPeriodFreeOfLessons(vocation) && isVocationsEndDateMoreThenStart(vocation)) {
	    vocationDao.update(vocation);
	}
    }

    public Vocation getById(int id) {
	return vocationDao.findById(id);
    }

    public List<Vocation> getAll() {
	return vocationDao.findAll();
    }

    public void delete(Vocation vocation) {
	vocationDao.deleteById(vocation.getId());
    }

    private boolean isVocationPeriodFreeOfLessons(Vocation vocation) {
	return lessonDao.findByTeacherBetweenDates(vocation.getTeacher(), vocation.getStart(), vocation.getEnd())
		.isEmpty();
    }

    private boolean isVocationsEndDateMoreThenStart(Vocation vocation) {
	return vocation.getStart().isBefore(vocation.getEnd());
    }

}
