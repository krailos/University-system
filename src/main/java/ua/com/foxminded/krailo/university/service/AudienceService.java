package ua.com.foxminded.krailo.university.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.NotUniqueNameException;
import ua.com.foxminded.krailo.university.model.Audience;

@Transactional
@Service
public class AudienceService {

    private static final Logger log = LoggerFactory.getLogger(AudienceService.class);

    private AudienceDao audienceDao;

    public AudienceService(AudienceDao audienceDao) {
	this.audienceDao = audienceDao;
    }

    public Audience getById(Integer id) {
	log.debug("get audience by id={}", id);
	return audienceDao.findById(id)
		.orElseThrow(() -> new EntityNotFoundException(String.format("Audience whith id=%s not exist", id)));
    }

    public Page<Audience> getAll(Pageable pageable) {
	log.debug("get audiences by page");
	return audienceDao.findAll(pageable);
    }
    
    public List<Audience> getAll() {
	log.debug("get all audiences");
	return audienceDao.findAll();
    }

    public void create(Audience audience) {
	log.debug("create audience={}", audience);
	checkAudienceNumberBeUnique(audience);
	audienceDao.save(audience);
    }

    public void delete(Audience audience) {
	log.debug("delete audience={}", audience);
	audienceDao.delete(audience);
    }

    public Audience getByNumber(String number) {
	log.debug("get audience by number={}", number);
	return audienceDao.findByNumber(number).orElseThrow(
		() -> new EntityNotFoundException(String.format("Audience whith number=%s not exist", number)));
    }

    private void checkAudienceNumberBeUnique(Audience audience) {
	Optional<Audience> existingAudience = audienceDao.findByNumber(audience.getNumber());
	log.debug("existing audience={}", existingAudience);
	if (existingAudience.filter(a -> a.getId() != audience.getId()).isPresent()) {
	    throw new NotUniqueNameException(String.format("audiences number=%s  not unique", audience.getNumber()));
	}
    }

}
