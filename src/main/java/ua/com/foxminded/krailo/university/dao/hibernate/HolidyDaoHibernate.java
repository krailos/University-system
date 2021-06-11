package ua.com.foxminded.krailo.university.dao.hibernate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.dao.interf.HolidayDaoInt;
import ua.com.foxminded.krailo.university.model.Holiday;

@Repository
public class HolidyDaoHibernate implements HolidayDaoInt {

    SessionFactory sessionFactory;

    public HolidyDaoHibernate(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Holiday> getById(int id) {
	Session session = sessionFactory.getCurrentSession();
	return Optional.ofNullable(session.get(Holiday.class, id));
    }

    @Override
    public void create(Holiday holiday) {
	Session session = sessionFactory.getCurrentSession();
	session.save(holiday);
    }

    @Override
    public void update(Holiday holiday) {
	Session session = sessionFactory.getCurrentSession();
	session.merge(holiday);
    }

    @Override
    public void delete(Holiday holiday) {
	Session session = sessionFactory.getCurrentSession();
	session.delete(holiday);

    }

    @Override
    public List<Holiday> getAll() {
	Session session = sessionFactory.getCurrentSession();
	Query<Holiday> query = session.createNamedQuery("SelectAllHolidays");
	return query.list();
    }

    @Override
    public Optional<Holiday> getByDate(LocalDate date) {
	Session session = sessionFactory.getCurrentSession();
	Query<Holiday> query = session.createNamedQuery("SelectHolidaysByDate");
	query.setParameter("date", date);
	try {
	    return Optional.of(query.getSingleResult());
	} catch (NoResultException e) {
	    return Optional.empty();
	}
    }

}
