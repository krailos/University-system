package ua.com.foxminded.krailo.university.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import ua.com.foxminded.krailo.university.model.Holiday;

public interface HolidayDao extends PagingAndSortingRepository<Holiday, Integer> {

    List<Holiday> findAll();

    Optional<Holiday> getByDate(LocalDate date);

}
