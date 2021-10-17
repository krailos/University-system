package ua.com.foxminded.krailo.university.dao;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.com.foxminded.krailo.university.model.Holiday;

public interface HolidayDao extends JpaRepository<Holiday, Integer> {

    Optional<Holiday> getByDate(LocalDate date);

}
