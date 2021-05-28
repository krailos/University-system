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

import ua.com.foxminded.krailo.university.dao.LessonDao;
import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.NoTeachersForSubstitute;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.Teacher;

@Service
public class TeacherService {

    private static final Logger log = LoggerFactory.getLogger(TeacherService.class);

    private TeacherDao teacherDao;
    private LessonDao lessonDao;

    public TeacherService(TeacherDao teacherDao, LessonDao lessonDao) {
	this.teacherDao = teacherDao;
	this.lessonDao = lessonDao;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void create(Teacher teacher) {
	log.debug("Create teacher={}", teacher);
	teacherDao.create(teacher);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(Teacher teacher) {
	log.debug("Update teacher={}", teacher);
	teacherDao.update(teacher);
    }

    public Teacher getById(int id) {
	log.debug("Get teacher by id={}", id);
	return teacherDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Teacher whith id=%s not exist", id)));
    }

    public List<Teacher> getAll() {
	log.debug("Get all teachers");
	return teacherDao.findAll();
    }

    public List<Teacher> getBySubjectId(int subjectId) {
	log.debug("Get teachers by subjectId={}", subjectId);
	return teacherDao.findBySubjectId(subjectId);
    }

    public void delete(Teacher teacher) {
	log.debug("Delete teacher={}", teacher);
	teacherDao.deleteById(teacher.getId());
    }

    public List<Teacher> findTeachersForSubstitute(Teacher substitutedTeacher, LocalDate startDate,
	    LocalDate finishDate) {
	List<Lesson> substitutedLessons = lessonDao.findByTeacherBetweenDates(substitutedTeacher, startDate,
		finishDate);
	List<Teacher> teachersForSubstitute = substitutedLessons.stream()
		.flatMap(l -> teacherDao.findBySubjectId(l.getSubject().getId()).stream()).distinct()
		.filter(t -> substitutedLessons.stream()
			.allMatch(l -> !lessonDao.findByDateAndTeacherIdAndLessonTimeId(l.getDate(), t.getId(),
				l.getLessonTime().getId()).isPresent()))
		.collect(Collectors.toList());
	if (teachersForSubstitute.isEmpty()) {
	    throw new NoTeachersForSubstitute("there is no free teachers");
	}
	return teachersForSubstitute;
    }

}
