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
	List<Audience> audiences = audienceDao.findByBuildingId(audience.getBuilding().getId());
	if (isAudienceNumberExist(audiences, audience)) {
	    audienceDao.create(audience);
	}
    }

    public void update(Audience audience) {
	List<Audience> audiences = audienceDao.findByBuildingId(audience.getBuilding().getId());
	if (isAudienceNumberExist(audiences, audience)) {
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

    private boolean isAudienceNumberExist(List<Audience> audiences, Audience audience) {
	return audiences.stream().map(Audience::getNumber).noneMatch(n -> n.equals(audience.getNumber()));
    }
}
