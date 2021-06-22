package ua.com.foxminded.krailo.university.dao.hibernate;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.dao.interf.VocationDao;
import ua.com.foxminded.krailo.university.model.Teacher;
import ua.com.foxminded.krailo.university.model.Vocation;

@Repository
public class VocationDaoHibernate implements VocationDao {

    private SessionFactory sessionFactory;

    public VocationDaoHibernate(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Vocation> getById(int id) {
	Session session = sessionFactory.getCurrentSession();
	return Optional.ofNullable(session.get(Vocation.class, id));
    }

    @Override
    public void create(Vocation vocation) {
	Session session = sessionFactory.getCurrentSession();
	session.save(vocation);
    }

    @Override
    public void update(Vocation vocation) {
	Session session = sessionFactory.getCurrentSession();
	session.merge(vocation);

    }

    @Override
    public void delete(Vocation vocation) {
	Session session = sessionFactory.getCurrentSession();
	session.delete(vocation);
    }

    @Override
    public List<Vocation> getAll() {
	Session session = sessionFactory.getCurrentSession();
	Query<Vocation> query = session.createNamedQuery("SelectAllVocations");
	return query.list();
    }

    @Override
    public List<Vocation> getByTeacher(Teacher teacher) {
	Session session = sessionFactory.getCurrentSession();
	Query<Vocation> query = session.createNamedQuery("SelectVocationsByTeacher");
	query.setParameter("teacherId", teacher.getId());
	return query.list();
    }

    @Override
    public List<Vocation> getByTeacherAndYear(Teacher teacher, Year year) {
	Session session = sessionFactory.getCurrentSession();
	Query<Vocation> query = session.createNamedQuery("SelectVocationsByTeacherAndYear");
	query.setParameter("teacherId", teacher.getId());
	query.setParameter("year", year.getValue());
	return query.list();
    }

    @Override
    public Optional<Vocation> getByTeacherAndDate(Teacher teacher, LocalDate date) {
	Session session = sessionFactory.getCurrentSession();
	Query<Vocation> query = session.createNamedQuery("SelectVocationsByTeacherAndDate");
	query.setParameter("teacherId", teacher.getId());
	query.setParameter("date", date);
	try {
	    return Optional.of(query.getSingleResult());
	} catch (NoResultException e) {
	    return Optional.empty();
	}
    }

    @Override
    public List<Vocation> getByPage(Pageable pageable) {
	return null;
    }

    @Override
    public int count() {
	return 0;
    }

}
