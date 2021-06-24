package ua.com.foxminded.krailo.university.dao.interf;

import java.time.LocalDate;
import java.util.Optional;

import ua.com.foxminded.krailo.university.model.Holiday;

public interface HolidayDao extends GenericDao<Holiday> {

    Optional<Holiday> getByDate(LocalDate date);
    
}
