package ua.com.foxminded.krailo.university.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Group;
import ua.com.foxminded.krailo.university.model.Year;

public interface GroupDao extends PagingAndSortingRepository<Group, Integer>, JpaRepository<Group, Integer> {

    List<Group> findByYear(Year year);

    Optional<Group> findByNameAndYear(String name, Year year);

}
