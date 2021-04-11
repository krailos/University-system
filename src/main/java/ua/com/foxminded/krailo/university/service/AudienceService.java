package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.NotUniqueNameException;
import ua.com.foxminded.krailo.university.model.Audience;
import ua.com.foxminded.krailo.university.util.Paging;

@Service
public class AudienceService {

    private static final Logger log = LoggerFactory.getLogger(AudienceService.class);

    private AudienceDao audienceDao;

    public AudienceService(AudienceDao audienceDao) {
	this.audienceDao = audienceDao;
    }

    public Audience getById(int id) {
	log.debug("get audience by id={}", id);
	return audienceDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Audience whith id=%s not exist", id)));
    }

    public Audience getByNumber(String number) {
	log.debug("get audience by number={}", number);
	return audienceDao.findByNumber(number)
		.orElseThrow(() -> new EntityNotFoundException(format("Audience whith number=%s not exist", number)));
    }

    public void create(Audience audience) {
	log.debug("create audience={}", audience);
	checkAudienceNumberBeUnique(audience);
	audienceDao.create(audience);

    }

    public void update(Audience audience) {
	log.debug("update audience={}", audience);
	checkAudienceNumberBeUnique(audience);
	audienceDao.update(audience);
    }

    public List<Audience> getAll() {
	log.debug("get all audiences");
	return audienceDao.findAll();
    }

    public void delete(Audience audience) {
	log.debug("delete audience={}", audience);
	audienceDao.deleteById(audience.getId());
    }

    public List<Audience> getByPage(Paging paging) {
	log.debug("get audiences by page");
	return audienceDao.findWithLimit(paging.getPageSize(), paging.getOffset());
    }

    public int getQuantity() {
	log.debug("get audience quantity");
	return audienceDao.findQuantity();
    }

    private void checkAudienceNumberBeUnique(Audience audience) {
	Optional<Audience> existingAudience = audienceDao.findByNumber(audience.getNumber());
	log.debug("existing audience={}", existingAudience);
	if (existingAudience.filter(a -> a.getId() != audience.getId()).isPresent()) {
	    throw new NotUniqueNameException(format("audiences number=%s  not unique", audience.getNumber()));
	}
    }

}
