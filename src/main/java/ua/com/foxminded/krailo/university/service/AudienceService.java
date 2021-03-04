package ua.com.foxminded.krailo.university.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.domain.Main;
import ua.com.foxminded.krailo.university.exception.ConstraintViolationException;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.QueryNotExecuteException;
import ua.com.foxminded.krailo.university.model.Audience;
import static java.lang.String.format;

@Service
public class AudienceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private AudienceDao audienceDao;

    public AudienceService(AudienceDao audienceDao) {
	this.audienceDao = audienceDao;
    }

    public void create(Audience audience) throws QueryNotExecuteException {
	LOGGER.debug("create audience '{}'", audience);
	if (isUniqueNumber(audience)) {
	    audienceDao.create(audience);
	} else {
	    String msg = format("audience not created, audience number %s not unique ", audience.getNumber());
	    LOGGER.debug(msg);
	    throw new ConstraintViolationException(msg);
	}
    }

    public void update(Audience audience) throws QueryNotExecuteException {
	LOGGER.debug("update audience '{}'", audience);
	if (isUniqueNumber(audience)) {
	    audienceDao.update(audience);
	} else {
	    String msg = format("audience not updated, audience number %s not unique ", audience.getNumber());
	    LOGGER.debug(msg);
	    throw new ConstraintViolationException(msg);
	}
    }

    public Audience getById(int id) throws QueryNotExecuteException, EntityNotFoundException {
	return audienceDao.findById(id);
    }

    public List<Audience> getAll() throws QueryNotExecuteException {
	return audienceDao.findAll();
    }

    public List<Audience> getByBuildingId(int id) throws QueryNotExecuteException {
	return audienceDao.findByBuildingId(id);
    }

    public void delete(Audience audience) throws QueryNotExecuteException {
	audienceDao.deleteById(audience.getId());
    }

    private boolean isUniqueNumber(Audience audience) {
	Optional<Audience> existingAudience = audienceDao.findByNumberAndBuildingId(audience.getNumber(),
		audience.getBuilding().getId());
	return (existingAudience.isEmpty() || existingAudience.filter(a -> a.getId() == audience.getId()).isPresent());
    }

}
