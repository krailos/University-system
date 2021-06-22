package ua.com.foxminded.krailo.university.dao.hibernate;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import ua.com.foxminded.krailo.university.dao.interf.YearDao;
import ua.com.foxminded.krailo.university.model.Year;

@Repository
public class YearDaoHibernate implements YearDao {

    private SessionFactory sessionFactory;

    public YearDaoHibernate(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Year> getById(int id) {
	Session session = sessionFactory.getCurrentSession();
	return Optional.ofNullable(session.get(Year.class, id));
    }

    @Override
    public void create(Year year) {
	Session session = sessionFactory.getCurrentSession();
	session.save(year);
    }

    @Override
    public void update(Year year) {
	Session session = sessionFactory.getCurrentSession();
	session.merge(year);

    }

    @Override
    public void delete(Year year) {
	Session session = sessionFactory.getCurrentSession();
	session.delete(year);
    }

    @Override
    public List<Year> getAll() {
	Session session = sessionFactory.getCurrentSession();
	Query<Year> query = session.createNamedQuery("SelectAllYears");
	return query.list();
    }

    @Override
    public List<Year> getByPage(Pageable pageable) {
	return null;
    }

    @Override
    public int count() {
	return 0;
    }

}
