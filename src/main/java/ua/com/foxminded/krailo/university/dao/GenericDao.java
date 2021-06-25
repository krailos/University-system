package ua.com.foxminded.krailo.university.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface GenericDao<Entity> {

    Optional<Entity> getById(int id);

    void create(Entity entity);

    void update(Entity entity);

    void delete(Entity entity);

    List<Entity> getAll();

    default Page<Entity> getAll(Pageable pageable) {
	return new PageImpl<>(new ArrayList<>());
    }

    default int count() {
	return 0;
    }

}
