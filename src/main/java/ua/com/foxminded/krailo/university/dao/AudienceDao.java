package ua.com.foxminded.krailo.university.dao;

import java.util.Optional;

import ua.com.foxminded.krailo.university.model.Audience;

public interface AudienceDao extends GenericDao<Audience> {

    Optional<Audience> getByNumber(String number);

}
