package ua.com.foxminded.krailo.university.dao.hibernate;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.dao.interf.SubjectDao;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Year;

@Repository
public class SubjectDaoHibernate implements SubjectDao {

    private SessionFactory sessionFactory;

    public SubjectDaoHibernate(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Subject> getById(int id) {
	Session session = sessionFactory.getCurrentSession();
	return Optional.ofNullable(session.get(Subject.class, id));
    }

    @Override
    public void create(Subject subject) {
	Session session = sessionFactory.getCurrentSession();
	session.save(subject);
    }

    @Override
    public void update(Subject subject) {
	Session session = sessionFactory.getCurrentSession();
	session.merge(subject);

    }

    @Override
    public void delete(Subject subject) {
	Session session = sessionFactory.getCurrentSession();
	session.delete(subject);
    }

    @Override
    public int count() {
	Session session = sessionFactory.getCurrentSession();
	Query<Long> query = session.createNamedQuery("CountAllSubjects");
	return query.uniqueResult().intValue();
    }

    @Override
    public List<Subject> getAll() {
	Session session = sessionFactory.getCurrentSession();
	Query<Subject> query = session.createNamedQuery("SelectAllSubjects");
	return query.list();
    }

    @Override
    public List<Subject> getByTeacher(Teacher teacher) {
	Session session = sessionFactory.getCurrentSession();
	Query<Subject> query = session.createNamedQuery("SelectSubjectsByTeacher");
	query.setParameter("teacherId", teacher.getId());
	return query.list();
    }

    @Override
    public List<Subject> getByYear(Year year) {
	Session session = sessionFactory.getCurrentSession();
	Query<Subject> query = session.createNamedQuery("SelectSubjectsByYear");
	query.setParameter("yearId", year.getId());
	return query.list();
    }

    @Override
    public List<Subject> getByPage(Pageable pageable) {
	return null;
    }

}
