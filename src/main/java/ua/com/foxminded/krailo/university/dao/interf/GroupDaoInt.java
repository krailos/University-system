package ua.com.foxminded.krailo.university.dao.interf;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Year;

public interface GroupDaoInt extends GenericDao<Group> {

    List<Group> getAll();
    
    List<Group> getByPage(Pageable pageable);

    List<Group> getByYear(Year year);

    Optional<Group> getByNameAndYear(String name, Year year);

    int count();

}
