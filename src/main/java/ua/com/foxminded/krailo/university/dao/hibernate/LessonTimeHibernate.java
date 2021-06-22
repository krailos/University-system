package ua.com.foxminded.krailo.university.dao.hibernate;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.dao.interf.LessonTimeDao;
import ua.com.foxminded.krailo.university.model.LessonTime;

@Repository
public class LessonTimeHibernate implements LessonTimeDao {

    SessionFactory sessionFactory;

    public LessonTimeHibernate(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<LessonTime> getById(int id) {
	Session session = sessionFactory.getCurrentSession();
	return Optional.ofNullable(session.get(LessonTime.class, id));
    }

    @Override
    public void create(LessonTime lessonTime) {
	Session session = sessionFactory.getCurrentSession();
	session.save(lessonTime);
    }

    @Override
    public void update(LessonTime lessonTime) {
	Session session = sessionFactory.getCurrentSession();
	session.merge(lessonTime);
    }

    @Override
    public void delete(LessonTime lessonTime) {
	Session session = sessionFactory.getCurrentSession();
	session.delete(lessonTime);
    }

    @Override
    public List<LessonTime> getAll() {
	Session session = sessionFactory.getCurrentSession();
	Query<LessonTime> query = session.getNamedQuery("SelectAllLessonTime");
	return query.list();
    }

    @Override
    public Optional<LessonTime> getByStartOrEndLessonTime(LessonTime lessonTime) {
	Session session = sessionFactory.getCurrentSession();
	Query<LessonTime> query = session.getNamedQuery("SelectLessonTimeByStartOrEnd");
	query.setParameter("startLessonTime", lessonTime.getStartTime());
	query.setParameter("endLessonTime", lessonTime.getEndTime());
	try {
	    return Optional.of(query.getSingleResult());
	} catch (NoResultException e) {
	    return Optional.empty();
	}
    }

    @Override
    public List<LessonTime> getByPage(Pageable pageable) {
	return null;
    }

    @Override
    public int count() {
	return 0;
    }

}
