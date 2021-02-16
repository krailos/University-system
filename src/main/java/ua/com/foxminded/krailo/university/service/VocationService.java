package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.VocationDao;
import ua.com.foxminded.krailo.university.model.Lesson;
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
	List<Lesson> lessons = lessonDao.findByVocationDate(vocation);
	if (checkByHavingLessons(lessons, vocation)) {
	    vocationDao.create(vocation);
	}
    }

    public void update(Vocation vocation) {
	List<Lesson> lessons = lessonDao.findByVocationDate(vocation);
	if (checkByHavingLessons(lessons, vocation)) {
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

    private boolean checkByHavingLessons(List<Lesson> lessons, Vocation vocation) {
	return lessons.stream().map(Lesson::getTeacher).noneMatch(t -> t.equals(vocation.getTeacher()));
    }

}
