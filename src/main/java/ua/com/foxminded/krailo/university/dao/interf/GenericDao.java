package ua.com.foxminded.krailo.university.dao.interf;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

public interface GenericDao<Dao> {

    Optional<Dao> getById(int id);

    void create(Dao dao);

    void update(Dao dao);

    void delete(Dao dao);
    
    List<Dao> getByPage(Pageable pageable);
    
    List<Dao> getAll();
    
    int count();

}
