package ua.com.foxminded.krailo.university.dao.hibernate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.dao.interf.LessonDaoInt;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Lesson;
import ua.com.foxminded.krailo.university.model.LessonTime;
import ua.com.foxminded.krailo.university.model.Student;
import ua.com.foxminded.krailo.university.model.Teacher;

@Repository
public class LessonDaoHibernate implements LessonDaoInt {

    SessionFactory sessionFactory;

    public LessonDaoHibernate(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Lesson> getById(int id) {
	Session session = sessionFactory.getCurrentSession();
	return Optional.ofNullable(session.get(Lesson.class, id));
    }

    @Override
    public void create(Lesson lesson) {
	Session session = sessionFactory.getCurrentSession();
	session.save(lesson);
    }

    @Override
    public void update(Lesson lesson) {
	Session session = sessionFactory.getCurrentSession();
	session.merge(lesson);
    }

    @Override
    public void delete(Lesson lesson) {
	Session session = sessionFactory.getCurrentSession();
	session.delete(lesson);
    }

    @Override
    public List<Lesson> getAll() {
	Session session = sessionFactory.getCurrentSession();
	Query<Lesson> query = session.getNamedQuery("SelectAllLessons");
	return query.list();
    }

    @Override
    public List<Lesson> getByPage(Pageable pageable) {
	Session session = sessionFactory.getCurrentSession();
	Query<Lesson> query = session.createNamedQuery("SelectAllLessons");
	query.setFirstResult((int) pageable.getOffset());
	query.setMaxResults(pageable.getPageSize());
	return query.list();
    }

    @Override
    public List<Lesson> getByDate(LocalDate date) {
	Session session = sessionFactory.getCurrentSession();
	Query<Lesson> query = session.getNamedQuery("SelectLessonsByDate");
	query.setParameter("date", date);
	return query.list();
    }

    @Override
    public List<Lesson> getByTeacherBetweenDates(Teacher teacher, LocalDate startDate, LocalDate finishDate) {
	Session session = sessionFactory.getCurrentSession();
	Query<Lesson> query = session.getNamedQuery("SelectLessonsByTeacherBetweenDates");
	query.setParameter("teacherId", teacher.getId());
	query.setParameter("startDate", startDate);
	query.setParameter("finishDate", finishDate);
	return query.list();
    }

    @Override
    public List<Lesson> getByTeacherAndDate(Teacher teacher, LocalDate date) {
	Session session = sessionFactory.getCurrentSession();
	Query<Lesson> query = session.getNamedQuery("SelectLessonsByTeacherAndDate");
	query.setParameter("teacherId", teacher.getId());
	query.setParameter("date", date);
	return query.list();
    }

    @Override
    public List<Lesson> getByStudentBetweenDates(Student student, LocalDate startDate, LocalDate finishDate) {
	Session session = sessionFactory.getCurrentSession();
	Query<Lesson> query = session
		.createNativeQuery("SELECT lessons.id, date, lesson_time_id, subject_id, teacher_id,"
			+ " audience_id, lesson_id, lessons_groups.group_id, students.id  FROM lessons"
			+ " JOIN lessons_groups ON (lessons.id = lessons_groups.lesson_id) JOIN students ON (lessons_groups.group_id = students.group_id) "
			+ " WHERE students.id = ?1 AND date between ?2 AND ?3")
		.addEntity(Lesson.class);
	query.setParameter(1, student.getId());
	query.setParameter(2, startDate);
	query.setParameter(3, finishDate);
	return query.list();
    }

    @Override
    public List<Lesson> getByStudentAndDate(Student student, LocalDate date) {
	Session session = sessionFactory.getCurrentSession();
	Query<Lesson> query = session
		.createNativeQuery("SELECT lessons.id, date, lesson_time_id, subject_id, teacher_id,"
			+ " audience_id, lesson_id, lessons_groups.group_id, students.id  FROM lessons"
			+ " JOIN lessons_groups ON (lessons.id = lessons_groups.lesson_id) JOIN students ON (lessons_groups.group_id = students.group_id) "
			+ " WHERE students.id = ? AND date = ?")
		.addEntity(Lesson.class);
	query.setParameter(1, student.getId());
	query.setParameter(2, date);
	return query.list();
    }

    @Override
    public Optional<Lesson> getByDateAndTeacherAndLessonTime(LocalDate date, Teacher teacher, LessonTime lessonTime) {
	Session session = sessionFactory.getCurrentSession();
	Query<Lesson> query = session.getNamedQuery("SelectLessonsByDateAndTeacherAndLessonTime");
	query.setParameter("date", date);
	query.setParameter("teacherId", teacher.getId());
	query.setParameter("lessonTimeId", lessonTime.getId());
	try {
	    return Optional.of(query.getSingleResult());
	} catch (NoResultException e) {
	    return Optional.empty();
	}
    }

    @Override
    public Optional<Lesson> getByDateAndAudienceAndLessonTime(LocalDate date, Audience audience,
	    LessonTime lessonTime) {
	Session session = sessionFactory.getCurrentSession();
	Query<Lesson> query = session.getNamedQuery("SelectLessonsByDateAndAudienceAndLessonTime");
	query.setParameter("date", date);
	query.setParameter("audienceId", audience.getId());
	query.setParameter("lessonTimeId", lessonTime.getId());
	try {
	    return Optional.of(query.getSingleResult());
	} catch (NoResultException e) {
	    return Optional.empty();
	}
    }

    @Override
    public Optional<Lesson> getByDateAndLessonTimeAndGroup(LocalDate date, LessonTime lessonTime, Group group) {
	Session session = sessionFactory.getCurrentSession();
	Query<Lesson> query = session.getNamedQuery("SelectLessonsByDateAndLessonTimeAndGroup");
	query.setParameter("date", date);
	query.setParameter("lessonTimeId", lessonTime.getId());
	query.setParameter("groupId", group.getId());
	try {
	    return Optional.of(query.getSingleResult());
	} catch (NoResultException e) {
	    return Optional.empty();
	}
    }

    @Override
    public int count() {
	Session session = sessionFactory.getCurrentSession();
	Query<Long> query = session.createNamedQuery("CountAllLessons");
	return query.uniqueResult().intValue();
    }

}
