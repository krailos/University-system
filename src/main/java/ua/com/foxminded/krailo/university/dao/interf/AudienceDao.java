package ua.com.foxminded.krailo.university.dao.interf;

import java.util.List;
import java.util.Optional;

import ua.com.foxminded.krailo.university.model.Audience;

public interface AudienceDao extends GenericDao<Audience> {

    Optional<Audience> getByNumber(String number);

    List<Audience> getAll();

    int count();
}
