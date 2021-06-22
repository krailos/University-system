package ua.com.foxminded.krailo.university.dao.interf;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ua.com.foxminded.krailo.university.model.Holiday;

public interface HolidayDao extends GenericDao<Holiday> {

    List<Holiday> getAll();

    Optional<Holiday> getByDate(LocalDate date);
}
