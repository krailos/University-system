package ua.com.foxminded.krailo.university.dao.hibernate;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.dao.GroupDao;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Year;

@Repository
public class HibernateGroupDao implements GroupDao {

    private SessionFactory sessionFactory;

    public HibernateGroupDao(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Group> getById(int id) {
	Session session = sessionFactory.getCurrentSession();
	return Optional.ofNullable(session.get(Group.class, id));
    }

    @Override
    public void create(Group group) {
	Session session = sessionFactory.getCurrentSession();
	session.save(group);
    }

    @Override
    public void update(Group group) {
	Session session = sessionFactory.getCurrentSession();
	session.merge(group);
    }

    @Override
    public void delete(Group group) {
	Session session = sessionFactory.getCurrentSession();
	session.delete(group);
    }

    @Override
    public List<Group> getAll() {
	Session session = sessionFactory.getCurrentSession();
	Query<Group> query = session.createNamedQuery("SelectAllGroups");
	return query.list();
    }

    @Override
    public List<Group> getByYear(Year year) {
	Session session = sessionFactory.getCurrentSession();
	Query<Group> query = session.createNamedQuery("SelectGroupsByYear");
	query.setParameter("yearId", year.getId());
	return query.list();
    }

    @Override
    public Optional<Group> getByNameAndYear(String name, Year year) {
	Session session = sessionFactory.getCurrentSession();
	Query<Group> query = session.createNamedQuery("SelectGroupsByNameAndYear");
	query.setParameter("name", name);
	query.setParameter("yearId", year.getId());
	try {
	    return Optional.of(query.getSingleResult());
	} catch (NoResultException e) {
	    return Optional.empty();
	}
    }

    @Override
    public int count() {
	Session session = sessionFactory.getCurrentSession();
	Query<Long> query = session.createNamedQuery("CountAllGroups");
	return query.uniqueResult().intValue();
    }

    @Override
    public Page<Group> getAll(Pageable pageable) {
	Session session = sessionFactory.getCurrentSession();
	Query<Group> query = session.createNamedQuery("SelectAllGroups");
	query.setFirstResult((int) pageable.getOffset());
	query.setMaxResults(pageable.getPageSize());
	return new PageImpl<>(query.list(), pageable, count());
    }

}
