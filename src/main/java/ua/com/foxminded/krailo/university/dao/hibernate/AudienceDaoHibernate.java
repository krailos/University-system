package ua.com.foxminded.krailo.university.dao.hibernate;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.dao.interf.AudienceDaoInt;
import ua.com.foxminded.krailo.university.model.Audience;

@Repository
public class AudienceDaoHibernate implements AudienceDaoInt {

    private SessionFactory sessionFactory;

    public AudienceDaoHibernate(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Audience> getById(int id) {
	Session session = sessionFactory.getCurrentSession();
	return Optional.ofNullable(session.get(Audience.class, id));
    }

    @Override
    public void create(Audience audience) {
	Session session = sessionFactory.getCurrentSession();
	session.save(audience);
    }

    @Override
    public void update(Audience audience) {
	Session session = sessionFactory.getCurrentSession();
	session.merge(audience);

    }

    @Override
    public void delete(Audience audience) {
	Session session = sessionFactory.getCurrentSession();
	session.delete(audience);
    }

    @Override
    public Optional<Audience> getByNumber(String number) {
	Session session = sessionFactory.getCurrentSession();
	Query<Audience> query = session.createNamedQuery("SelectAudienceByNumber");
	query.setParameter("number", number);
	try {
	    return Optional.of(query.getSingleResult());
	} catch (NoResultException e) {
	    return Optional.empty();
	}
    }

    @Override
    public List<Audience> getAllByPage(Pageable pageable) {
	Session session = sessionFactory.getCurrentSession();
	Query<Audience> query = session.createNamedQuery("SelectAllAudiences");
	query.setFirstResult((int) pageable.getOffset());
	query.setMaxResults(pageable.getPageSize());
	return query.list();
    }

    @Override
    public int count() {
	Session session = sessionFactory.getCurrentSession();
	Query<Long> query = session.createNamedQuery("CountAllAudiences");
	return query.uniqueResult().intValue();
    }

    @Override
    public List<Audience> getAll() {
	Session session = sessionFactory.getCurrentSession();
	Query<Audience> query = session.createNamedQuery("SelectAllAudiences");
	return query.list();
    }

}
