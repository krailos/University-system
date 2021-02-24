package ua.com.foxminded.krailo.university.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.TimetableDao;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Teacher;
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

    public Timetable getTimetableForTeacherByDate(int timetableId, Teacher teacher, LocalDate date) {
	Timetable timetable = timetableDao.findById(timetableId);
	List<Lesson> lessons = lessonDao.findByTeacherByDate(teacher, date);
	timetable.setLessons(lessons);
	timetable.setTeacher(teacher);
	return timetable;
    }

    public Timetable getTimetableForTeacherByMonth(int timetableId, Teacher teacher, LocalDate date) {
	Timetable timetable = timetableDao.findById(timetableId);
	List<Lesson> lessons = lessonDao.findByTeacherByMonth(teacher, date);
	timetable.setLessons(lessons);
	timetable.setTeacher(teacher);
	return timetable;
    }

    public Timetable getTimetableForStudentByDate(int timetableId, Student student, LocalDate date) {
	Timetable timetable = timetableDao.findById(timetableId);
	List<Lesson> lessons = lessonDao.findByStudentByDate(student, date);
	timetable.setLessons(lessons);
	timetable.setStudent(student);
	return timetable;
    }

    public Timetable getTimetableForStudentByMonth(int timetableId, Student student, LocalDate date) {
	Timetable timetable = timetableDao.findById(timetableId);
	List<Lesson> lessons = lessonDao.findByStudentByMonth(student, date);
	timetable.setLessons(lessons);
	timetable.setStudent(student);
	return timetable;
    }

}
