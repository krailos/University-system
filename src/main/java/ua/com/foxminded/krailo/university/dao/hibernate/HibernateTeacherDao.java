package ua.com.foxminded.krailo.university.dao.hibernate;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.dao.TeacherDao;
import ua.com.foxminded.krailo.university.model.Subject;
import ua.com.foxminded.krailo.university.model.Teacher;

@Repository
public class HibernateTeacherDao implements TeacherDao {

    private SessionFactory sessionFactory;

    public HibernateTeacherDao(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Teacher> getById(int id) {
	Session session = sessionFactory.getCurrentSession();
	return Optional.ofNullable(session.get(Teacher.class, id));
    }

    @Override
    public void create(Teacher teacher) {
	Session session = sessionFactory.getCurrentSession();
	session.save(teacher);
    }

    @Override
    public void update(Teacher teacher) {
	Session session = sessionFactory.getCurrentSession();
	session.merge(teacher);

    }

    @Override
    public void delete(Teacher teacher) {
	Session session = sessionFactory.getCurrentSession();
	session.delete(teacher);
    }

    @Override
    public int count() {
	Session session = sessionFactory.getCurrentSession();
	Query<Long> query = session.createNamedQuery("CountAllTeachers");
	return query.uniqueResult().intValue();
    }

    @Override
    public List<Teacher> getAll() {
	Session session = sessionFactory.getCurrentSession();
	Query<Teacher> query = session.createNamedQuery("SelectAllTeachers");
	return query.list();
    }

    @Override
    public List<Teacher> getBySubject(Subject subject) {
	Session session = sessionFactory.getCurrentSession();
	Query<Teacher> query = session.createNamedQuery("SelectTeachersBySubject");
	query.setParameter("subjectId", subject.getId());
	return query.list();
    }

}
