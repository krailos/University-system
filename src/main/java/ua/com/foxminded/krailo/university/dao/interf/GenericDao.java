package ua.com.foxminded.krailo.university.dao.interf;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

public interface GenericDao<Entity> {

    Optional<Entity> getById(int id);

    void create(Entity entity);

    void update(Entity entity);

    void delete(Entity entity);

    List<Entity> getByPage(Pageable pageable);

    List<Entity> getAll();

    int count();

}
