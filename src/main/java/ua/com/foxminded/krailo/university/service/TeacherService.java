package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.dao.interf.LessonDaoInt;
import ua.com.foxminded.krailo.university.dao.interf.TeacherDaoInt;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;

@Transactional
@Service
public class TeacherService {

    private static final Logger log = LoggerFactory.getLogger(TeacherService.class);

    private TeacherDaoInt teacherDaoInt;
    private LessonDaoInt lessonDaoInt;

    public TeacherService(TeacherDaoInt teacherDaoInt, LessonDaoInt lessonDaoInt) {
	this.teacherDaoInt = teacherDaoInt;
	this.lessonDaoInt = lessonDaoInt;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void create(Teacher teacher) {
	log.debug("Create teacher={}", teacher);
	teacherDaoInt.create(teacher);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(Teacher teacher) {
	log.debug("Update teacher={}", teacher);
	teacherDaoInt.update(teacher);
    }

    public Teacher getById(int id) {
	log.debug("Get teacher by id={}", id);
	return teacherDaoInt.getById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Teacher whith id=%s not exist", id)));
    }

    public List<Teacher> getAll() {
	log.debug("Get all teachers");
	return teacherDaoInt.getAll();
    }

    public List<Teacher> getBySubjectId(Subject subject) {
	log.debug("Get teachers by subjectId={}", subject.getId());
	return teacherDaoInt.getBySubject(subject);
    }

    public void delete(Teacher teacher) {
	log.debug("Delete teacher={}", teacher);
	teacherDaoInt.delete(teacher);
    }

    public List<Teacher> findTeachersForSubstitute(Teacher substitutedTeacher, LocalDate startDate,
	    LocalDate finishDate) {
	List<Lesson> substitutedLessons = lessonDaoInt.getByTeacherBetweenDates(substitutedTeacher, startDate,
		finishDate);
	List<Subject> substitutedSubjects = substitutedLessons.stream().map(Lesson::getSubject)
		.collect(Collectors.toList());
	List<Teacher> teachersForSubstitute = substitutedSubjects.stream()
		.flatMap(s -> teacherDaoInt.getBySubject(s).stream()).distinct()
		.filter(t -> t.getSubjects().containsAll(substitutedSubjects)).collect(Collectors.toList());
	return teachersForSubstitute.stream()
		.filter(t -> substitutedLessons.stream().noneMatch(l -> lessonDaoInt
			.getByDateAndTeacherAndLessonTime(l.getDate(), t, l.getLessonTime()).isPresent()))
		.collect(Collectors.toList());
    }

}
