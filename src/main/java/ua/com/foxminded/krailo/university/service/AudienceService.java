package ua.com.foxminded.krailo.university.service;

import static java.lang.String.format;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.com.foxminded.krailo.university.dao.AudienceDaoInt;
import ua.com.foxminded.krailo.university.exception.EntityNotFoundException;
import ua.com.foxminded.krailo.university.exception.NotUniqueNameException;
import ua.com.foxminded.krailo.university.model.Audience;

@Transactional
@Service
public class AudienceService {

    private static final Logger log = LoggerFactory.getLogger(AudienceService.class);

    private AudienceDaoInt audienceDaoInt;

    public AudienceService(AudienceDaoInt audienceDaoInt) {
	this.audienceDaoInt = audienceDaoInt;
    }

    public Audience getById(int id) {
	log.debug("get audience by id={}", id);
	return audienceDaoInt.getById(id)
		.orElseThrow(() -> new EntityNotFoundException(format("Audience whith id=%s not exist", id)));
    }

    public Audience getByNumber(String number) {
	log.debug("get audience by number={}", number);
	return audienceDaoInt.getByNumber(number)
		.orElseThrow(() -> new EntityNotFoundException(format("Audience whith number=%s not exist", number)));
    }

    public void create(Audience audience) {
	log.debug("create audience={}", audience);
	checkAudienceNumberBeUnique(audience);
	audienceDaoInt.create(audience);

    }

    public void update(Audience audience) {
	log.debug("update audience={}", audience);
	checkAudienceNumberBeUnique(audience);
	audienceDaoInt.update(audience);
    }

    public List<Audience> getAll() {
	log.debug("get all audiences");
	return audienceDaoInt.getAll();
    }

    public Page<Audience> getSelectedPage(Pageable pageable) {
	log.debug("get audiences by page");
	return new PageImpl<>(audienceDaoInt.getAllByPage(pageable), pageable, audienceDaoInt.count());
    }

    public void delete(Audience audience) {
	log.debug("delete audience={}", audience);
	audienceDaoInt.delete(audience);
    }

    private void checkAudienceNumberBeUnique(Audience audience) {
	Optional<Audience> existingAudience = audienceDaoInt.getByNumber(audience.getNumber());
	log.debug("existing audience={}", existingAudience);
	if (existingAudience.filter(a -> a.getId() != audience.getId()).isPresent()) {
	    throw new NotUniqueNameException(format("audiences number=%s  not unique", audience.getNumber()));
	}
    }

}
