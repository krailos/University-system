package ua.com.foxminded.krailo.university.dao;

import java.util.List;
import java.util.Optional;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Year;

public interface GroupDao extends GenericDao<Group> {
    
    List<Group> getByYear(Year year);

    Optional<Group> getByNameAndYear(String name, Year year);

}
