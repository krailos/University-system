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
    private boolean checkPassed = true;

    public LessonService(LessonDao lessonDao, GroupDao groupDao) {
	this.lessonDao = lessonDao;
	this.groupDao = groupDao;
    }

    public void create(Lesson lesson) {
	List<Lesson> lessons = lessonDao.findAll();
	checkBySubject(lesson);
	checkByLessonTime(lessons, lesson);
	checkByTeacher(lessons, lesson);
	checkByGroup(lesson);
	checkByGroupCapacity(lesson);
	if (checkPassed) {
	    lessonDao.create(lesson);
	}
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
	if (checkPassed) {
	    lessonDao.update(lesson);
	}
    }

    public Lesson getById(int id) {
	return lessonDao.findById(id);
    }

    public List<Lesson> getAll() {
	return lessonDao.findAll();
    }

    private void checkBySubject(Lesson lesson) {
	if (!lesson.getTimetable().getYear().getSubjects().contains(lesson.getSubject())) {
	    System.out.println("coz subject");
	    checkPassed = false;
	}
    }

    private void checkByLessonTime(List<Lesson> lessons, Lesson lesson) {
	if (lessons.stream().filter(l -> lesson.getDate().equals(l.getDate()))
		.filter(l -> lesson.getAudience().equals(l.getAudience()))
		.filter(l -> lesson.getLessonTime().equals(l.getLessonTime())).count() > 0) {
	    System.out.println("coz lessontime");
	    checkPassed = false;
	}
    }

    private void checkByTeacher(List<Lesson> lessons, Lesson lesson) {
	if (lessons.stream().filter(l -> lesson.getDate().equals(l.getDate()))
		.filter(l -> lesson.getLessonTime().equals(l.getLessonTime()))
		.filter(l -> lesson.getTeacher().equals(l.getTeacher())).count() > 0) {
	    System.out.println("coz teacher");
	    checkPassed = false;
	}
    }

    private void checkByGroup(Lesson lesson) {
	if (lesson.getGroups().stream().filter(g ->!lesson.getTimetable().getYear().getGroups().contains(g)).count() > 0) {
	    System.out.println("coz group");
	    checkPassed = false;
	}
    }

    private void checkByGroupCapacity(Lesson lesson) {
	if (lesson.getAudience().getCapacity() < lesson.getGroups().stream().mapToInt(g -> g.getStudents().size())
		.sum()) {
	    System.out.println("coz capacity");
	    checkPassed = false;
	}
    }
    
}
