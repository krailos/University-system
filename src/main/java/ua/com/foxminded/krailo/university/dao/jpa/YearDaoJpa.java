package ua.com.foxminded.krailo.university.dao.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Year;

public interface YearDaoJpa extends PagingAndSortingRepository<Year, Integer> {

    Optional<Year> findById(int id);

    List<Year> findAll();

    Year save(Year year);

    void delete(Year year);

}
