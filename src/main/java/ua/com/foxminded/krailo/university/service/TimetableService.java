package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.TimetableDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.ServiceException;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Timetable;

@Service
public class TimetableService {

    private static final Logger log = LoggerFactory.getLogger(TimetableService.class);

    private TimetableDao timetableDao;
    private LessonDao lessonDao;

    public TimetableService(TimetableDao timetableDao, LessonDao lessonDao) {
	this.timetableDao = timetableDao;
	this.lessonDao = lessonDao;
    }

    public void create(Timetable timetable) {
	log.debug("Create timetable={}", timetable);
	timetableDao.create(timetable);
    }

    public void update(Timetable timetable) {
	log.debug("Update timetable={}", timetable);
	timetableDao.update(timetable);
    }

    public Timetable getById(int id) {
	log.debug("Get timetable by id={}", id);
	Optional<Timetable> existingTimetable = timetableDao.findById(id);
	if (existingTimetable.isPresent()) {
	    return existingTimetable.get();
	} else {
	    throw new ServiceException(format("timetable with id=%s not exist", id));
	}
    }

    public List<Timetable> getAll() {
	log.debug("Get all departments");
	return timetableDao.findAll();
    }

    public void delete(Timetable timetable) {
	log.debug("Delete timetable={}", timetable);
	timetableDao.deleteById(timetable.getId());
    }

    public Timetable getTimetableForTeacherByDate(int timetableId, Teacher teacher, LocalDate date) {
	log.debug("get timetable for teacher={} by date={}", teacher, date);
	Timetable timetable = timetableDao.findById(timetableId).orElseThrow(()-> new EntityNotFoundException("timetable not found"));
	List<Lesson> lessons = lessonDao.findByTeacherAndDate(teacher, date);
	timetable.setLessons(lessons);
	timetable.setTeacher(teacher);
	return timetable;
    }

    public Timetable getTimetableForTeacherByMonth(int timetableId, Teacher teacher, LocalDate date) {
	log.debug("get timetable for teacher={} by month", teacher);
	Timetable timetable = timetableDao.findById(timetableId).orElseThrow(()-> new EntityNotFoundException("timetable not found"));
	List<Lesson> lessons = lessonDao.findByTeacherBetweenDates(teacher, date, date.plusMonths(1));
	timetable.setLessons(lessons);
	timetable.setTeacher(teacher);
	return timetable;
    }

    public Timetable getTimetableForStudentByDate(int timetableId, Student student, LocalDate date) {
	log.debug("get timetable for student={} by date={}", student, date);
	Timetable timetable = timetableDao.findById(timetableId).orElseThrow(()-> new EntityNotFoundException("timetable not found"));
	List<Lesson> lessons = lessonDao.findByStudentAndDate(student, date);
	timetable.setLessons(lessons);
	timetable.setStudent(student);
	return timetable;
    }

    public Timetable getTimetableForStudentByMonth(int timetableId, Student student, LocalDate date) {
	log.debug("get timetable for student={} by month", student);
	Timetable timetable = timetableDao.findById(timetableId).orElseThrow(()-> new EntityNotFoundException("timetable not found"));
	List<Lesson> lessons = lessonDao.findByStudentBetweenDates(student, date, date.plusMonths(1));
	timetable.setLessons(lessons);
	timetable.setStudent(student);
	return timetable;
    }

}
