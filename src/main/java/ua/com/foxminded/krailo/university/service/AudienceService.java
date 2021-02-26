package ua.com.foxminded.krailo.university.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.model.Audience;

@Service
public class AudienceService {

    private AudienceDao audienceDao;

    public AudienceService(AudienceDao audienceDao) {
	this.audienceDao = audienceDao;
    }

    public void create(Audience audience) {
	if (isUniqueNumber(audience)) {
	    audienceDao.create(audience);
	}
    }

    public void update(Audience audience) {
	if (isUniqueNumber(audience)) {
	    audienceDao.update(audience);
	}
    }

    public Audience getById(int id) {
	return audienceDao.findById(id);
    }

    public List<Audience> getAll() {
	return audienceDao.findAll();
    }

    public List<Audience> getByBuildingId(int id) {
	return audienceDao.findByBuildingId(id);
    }

    public void delete(Audience audience) {
	audienceDao.deleteById(audience.getId());
    }

    private boolean isUniqueNumber(Audience audience) {
	Optional<Audience> existingAudience = Optional.ofNullable(audienceDao.findByNumberAndBuildingId(audience.getNumber(),
		audience.getBuilding().getId()));
	return (existingAudience.isEmpty() || existingAudience.filter(a -> a.getId() == audience.getId()).isPresent());
    }

}
