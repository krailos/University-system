package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.exception.ConstraintViolationException;
import ua.com.foxminded.krailo.university.exception.DaoException;
import ua.com.foxminded.krailo.university.exception.ServiceException;
import ua.com.foxminded.krailo.university.model.Audience;

@Service
public class AudienceService {

    private static final Logger log = LoggerFactory.getLogger(AudienceService.class);

    private AudienceDao audienceDao;

    public AudienceService(AudienceDao audienceDao) {
	this.audienceDao = audienceDao;
    }

    public Audience getById(int id) {
	log.debug("get audience by id={}", id);
	return audienceDao.findById(id).get();
    }

    public void create(Audience audience) {
	log.debug("create audience={}", audience);
	if (isUniqueNumber(audience)) {
	    audienceDao.create(audience);
	}
    }

    public void update(Audience audience) {
	log.debug("update audience='{}'", audience);
	if (isUniqueNumber(audience)) {
	    audienceDao.update(audience);
	}
    }

    public List<Audience> getAll() {
	log.debug("get all audiences");
	return audienceDao.findAll();
    }

    public List<Audience> getByBuildingId(int id) {
	log.debug("get audiences by building id={}", id);
	return audienceDao.findByBuildingId(id);
    }

    public void delete(Audience audience) {
	log.debug("delete audience={}", audience);
	audienceDao.deleteById(audience.getId());
    }

    private boolean isUniqueNumber(Audience audience) {
	log.debug("is unique audience number");
	Optional<Audience> existingAudience = audienceDao.findByNumberAndBuildingId(audience.getNumber(),
		audience.getBuilding().getId());
	if (existingAudience.isEmpty() || existingAudience.filter(a -> a.getId() == audience.getId()).isPresent()) {
	    return true;
	} else {
	    throw new ConstraintViolationException(format("Not unique audiences whith number=%s and building id=%s",
		    audience.getNumber(), audience.getBuilding().getId()));
	}
    }

}
