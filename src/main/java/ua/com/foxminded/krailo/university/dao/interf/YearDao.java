package ua.com.foxminded.krailo.university.dao.interf;

import java.util.List;

import ua.com.foxminded.krailo.university.model.Year;

public interface YearDao extends GenericDao<Year> {

    List<Year> getAll();

}
