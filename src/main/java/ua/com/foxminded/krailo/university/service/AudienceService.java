package ua.com.foxminded.krailo.university.service;

import java.util.List;

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
	if (isUniqueNumberForUpdate(audience)) {
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
	return audienceDao.findByNumberAndBuildingId(audience.getNumber(), audience.getBuilding().getId()) == null;
    }

    private boolean isUniqueNumberForUpdate(Audience audience) {
	Audience audienceFromDB = audienceDao.findByNumberAndBuildingId(audience.getNumber(),
		audience.getBuilding().getId());
	return (audienceFromDB == null || audienceFromDB.getId() == audience.getId());

    }
}
