package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;

@Service
public class LessonService {

    private LessonDao lessonDao;

    public LessonService(LessonDao lessonDao) {
	this.lessonDao = lessonDao;
    }

    public void create(Lesson lesson) {
	List<Lesson> lessons = lessonDao.findByDate(lesson);
	if (checkBySubject(lesson) && checkByLessonTime(lessons, lesson) && checkByTeacher(lessons, lesson)
		&& checkByGroup(lesson) && checkByGroupCapacity(lesson)) {
	    lessonDao.create(lesson);
	}
    }

    public void update(Lesson lesson) {
	List<Lesson> lessons = lessonDao.findByDate(lesson);
	if (checkBySubject(lesson) && checkByLessonTime(lessons, lesson) && checkByTeacher(lessons, lesson)
		&& checkByGroup(lesson) && checkByGroupCapacity(lesson)) {
	    lessonDao.update(lesson);
	}
    }

    public Lesson getById(int id) {
	return lessonDao.findById(id);
    }

    public List<Lesson> getAll() {
	return lessonDao.findAll();
    }

    public void delete(Lesson lesson) {
	lessonDao.deleteById(lesson.getId());
    }

    private boolean checkBySubject(Lesson lesson) {
	return lesson.getTimetable().getYear().getSubjects().contains(lesson.getSubject());
    }

    private boolean checkByLessonTime(List<Lesson> lessons, Lesson lesson) {
	return lessons.stream().filter(l -> lesson.getAudience().equals(l.getAudience())).map(Lesson::getLessonTime)
		.noneMatch(t -> t.equals(lesson.getLessonTime()));
    }

    private boolean checkByTeacher(List<Lesson> lessons, Lesson lesson) {
	return lessons.stream().filter(l -> lesson.getLessonTime().equals(l.getLessonTime())).map(Lesson::getTeacher)
		.noneMatch(t -> t.equals(lesson.getTeacher()));
    }

    private boolean checkByGroup(Lesson lesson) {
	return lesson.getGroups().stream().allMatch(g -> lesson.getTimetable().getYear().getGroups().contains(g));
    }

    private boolean checkByGroupCapacity(Lesson lesson) {
	return (lesson.getAudience().getCapacity() >= lesson.getGroups().stream().map(Group::getStudents)
		.mapToInt(List::size).sum());
    }

}
