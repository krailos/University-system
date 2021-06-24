package ua.com.foxminded.krailo.university.dao.hibernate;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.dao.interf.StudentDao;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Student;

@Repository
public class HibernateStudentDao implements StudentDao {

    SessionFactory sessionFactory;

    public HibernateStudentDao(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Student> getById(int id) {
	Session session = sessionFactory.getCurrentSession();
	return Optional.ofNullable(session.get(Student.class, id));
    }

    @Override
    public void create(Student student) {
	Session session = sessionFactory.getCurrentSession();
	session.save(student);

    }

    @Override
    public void update(Student student) {
	Session session = sessionFactory.getCurrentSession();
	session.merge(student);

    }

    @Override
    public void delete(Student student) {
	Session session = sessionFactory.getCurrentSession();
	session.delete(student);
    }

    @Override
    public List<Student> getByPage(Pageable pageable) {
	Session session = sessionFactory.getCurrentSession();
	Query<Student> query = session.createNamedQuery("SelectAllStudents");
	query.setFirstResult((int) pageable.getOffset());
	query.setMaxResults(pageable.getPageSize());
	return query.list();
    }

    @Override
    public List<Student> getAll() {
	Session session = sessionFactory.getCurrentSession();
	Query<Student> query = session.createNamedQuery("SelectAllStudents");
	return query.list();
    }

    @Override
    public List<Student> getByGroup(Group group) {
	Session session = sessionFactory.getCurrentSession();
	Query<Student> query = session.createNamedQuery("SelectStudentsByGroup");
	query.setParameter("groupId", group.getId());
	return query.list();
    }

    @Override
    public int count() {
	Session session = sessionFactory.getCurrentSession();
	Query<Long> query = session.createNamedQuery("CountAllStudents");
	return query.uniqueResult().intValue();
    }

}
