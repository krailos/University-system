package ua.com.foxminded.krailo.university.dao.interf;

import java.util.Optional;

public interface GenericDao<T> {

    Optional<T> getById(int id);

    void create(T t);

    void update(T t);

    void delete(T t);

}
