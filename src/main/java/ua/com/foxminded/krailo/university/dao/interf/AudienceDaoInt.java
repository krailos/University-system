package ua.com.foxminded.krailo.university.dao.interf;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import ua.com.foxminded.krailo.university.model.Audience;

public interface AudienceDaoInt extends GenericDao<Audience> {

    Optional<Audience> getByNumber(String number);

    List<Audience> getAllByPage(Pageable pageable);
    
    List<Audience> getAll();
    
    int count();
}
