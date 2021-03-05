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

    public Audience getById(int id) throws ServiceException {
	log.debug("get audience by id='{}'", id);
	try {
	    return audienceDao.findById(id).get();
	} catch (DaoException e) {
	    throw new ServiceException(e.getMessage(), e);
	}
    }

    public void create(Audience audience) throws DaoException {
	log.debug("create audience='{}'", audience);
	if (isUniqueNumber(audience))
	    audienceDao.create(audience);
    }

    public void update(Audience audience) throws DaoException {
	log.debug("update audience='{}'", audience);
	if (isUniqueNumber(audience))
	    audienceDao.update(audience);
    }

    public List<Audience> getAll() throws DaoException {
	log.debug("get all audiences");
	return audienceDao.findAll();
    }

    public List<Audience> getByBuildingId(int id) throws DaoException {
	log.debug("get audiences by building id='{}'", id);
	return audienceDao.findByBuildingId(id);
    }

    public void delete(Audience audience) throws DaoException {
	log.debug("delete audience='{}'", audience);
	audienceDao.deleteById(audience.getId());
    }

    private boolean isUniqueNumber(Audience audience) throws ConstraintViolationException, ServiceException {
	log.debug("is unique audience number");
	try {
	    Optional<Audience> existingAudience = audienceDao.findByNumberAndBuildingId(audience.getNumber(),
		    audience.getBuilding().getId());
	    if (existingAudience.isEmpty() || existingAudience.filter(a -> a.getId() == audience.getId()).isPresent()) {
		log.debug("audience number is unique");
		return true;
	    } else {
		String msg = format("Not unique audiences whith number=%s and building id=%s", audience.getNumber(),
			audience.getBuilding().getId());
		throw new ConstraintViolationException(msg);
	    }
	} catch (DaoException e) {
	    throw new ServiceException(e.getMessage(), e);
	}
    }

}
