package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.model.Lesson;

@Service
public class LessonService {

    private LessonDao lessonDao;
    private GroupDao groupDao;

    public LessonService(LessonDao lessonDao, GroupDao groupDao) {
	this.lessonDao = lessonDao;
	this.groupDao = groupDao;
    }

    public void create(Lesson lesson) {
	List<Lesson> lessons = lessonDao.findAll();
	checkBySubject(lesson);
	checkByLessonTime(lessons, lesson);
	checkByTeacher(lessons, lesson);
	checkByGroupCapacity(lesson);
	lessonDao.create(lesson);
    }

    public void addGroup(Lesson lesson, int groupId) {
	lesson.getGroups().add(groupDao.findById(groupId));
	lessonDao.update(lesson);
    }

    public void update(Lesson lesson) {
	List<Lesson> lessons = lessonDao.findAll();
	checkBySubject(lesson);
	checkByLessonTime(lessons, lesson);
	checkByTeacher(lessons, lesson);
	checkByGroupCapacity(lesson);
	lessonDao.update(lesson);
    }

    public Lesson getById(int id) {
	return lessonDao.findById(id);
    }

    public List<Lesson> getAll() {
	return lessonDao.findAll();
    }

    private void checkBySubject(Lesson lesson) {
	if (!lesson.getTimetable().getYear().getSubjects().contains(lesson.getSubject())) {
	    return;
	}
    }

    private void checkByLessonTime(List<Lesson> lessons, Lesson lesson) {
	if (lessons.stream().filter(l -> lesson.getDate().equals(l.getDate()))
		.filter(l -> lesson.getAudience().equals(l.getAudience()))
		.filter(l -> lesson.getLessonTime().equals(l.getLessonTime())).count() > 0) {
	    return;
	}
    }

    private void checkByTeacher(List<Lesson> lessons, Lesson lesson) {
	if (lessons.stream().filter(l -> lesson.getDate().equals(l.getDate()))
		.filter(l -> lesson.getLessonTime().equals(l.getLessonTime()))
		.filter(l -> lesson.getTeacher().equals(l.getTeacher())).count() > 0) {
	    return;
	}
    }

    private void checkByGroupCapacity(Lesson lesson) {
	if (lesson.getAudience().getCapacity() < lesson.getGroups().stream().mapToInt(g -> g.getStudents().size())
		.sum()) {
	    return;
	}
    }

}
