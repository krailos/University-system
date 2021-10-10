package ua.com.foxminded.krailo.university.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.dao.jpa.LessonDaoJpa;
import ua.com.foxminded.krailo.university.dao.jpa.TeacherDaoJpa;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;

@Transactional
@Service
public class TeacherService {

    private static final Logger log = LoggerFactory.getLogger(TeacherService.class);

    private TeacherDaoJpa teacherDao;
    private LessonDaoJpa lessonDao;

    public TeacherService(TeacherDaoJpa teacherDao, LessonDaoJpa lessonDao) {
	this.teacherDao = teacherDao;
	this.lessonDao = lessonDao;
    }

    public Teacher getById(int id) {
	log.debug("Get teacher by id={}", id);
	return teacherDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(String.format("Teacher whith id=%s not exist", id)));
    }

    public List<Teacher> getAll() {
	log.debug("Get all teachers");
	return (List<Teacher>) teacherDao.findAll();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void create(Teacher teacher) {
	log.debug("Create teacher={}", teacher);
	teacherDao.save(teacher);
    }

    public List<Teacher> getBySubjectId(Subject subject) {
	log.debug("Get teachers by subjectId={}", subject.getId());
	return teacherDao.getBySubject(subject.getId());
    }

    public void delete(Teacher teacher) {
	log.debug("Delete teacher={}", teacher);
	teacherDao.delete(teacher);
    }

    public List<Teacher> findTeachersForSubstitute(Teacher substitutedTeacher, LocalDate startDate,
	    LocalDate finishDate) {
	List<Lesson> substitutedLessons = lessonDao.getByTeacherBetweenDates(substitutedTeacher.getId(), startDate,
		finishDate);
	List<Subject> substitutedSubjects = substitutedLessons.stream().map(Lesson::getSubject)
		.collect(Collectors.toList());
	List<Teacher> teachersForSubstitute = substitutedSubjects.stream()
		.flatMap(s -> teacherDao.getBySubject(s.getId()).stream()).distinct()
		.filter(t -> t.getSubjects().containsAll(substitutedSubjects)).collect(Collectors.toList());
	return teachersForSubstitute.stream()
		.filter(t -> substitutedLessons.stream().noneMatch(l -> lessonDao
			.getByDateAndTeacherAndLessonTime(l.getDate(), t.getId(), l.getLessonTime().getId()).isPresent()))
		.collect(Collectors.toList());
    }

}
