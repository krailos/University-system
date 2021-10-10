package ua.com.foxminded.krailo.university.dao.jpa;


import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Year;

public interface GroupDaoJpa extends PagingAndSortingRepository<Group, Integer> {

    Optional<Group> findById(int id);

    Page<Group> findAll(Pageable pageable);

    Group save(Group group);

    void delete(Group group);
    
    List<Group> findByYear (Year year);
    
    Optional<Group> findByNameAndYear (String name, Year year);

}
