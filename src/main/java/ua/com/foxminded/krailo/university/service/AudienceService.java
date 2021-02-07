package ua.com.foxminded.krailo.university.service;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.AudienceDao;
import ua.com.foxminded.krailo.university.model.Audience;

@Service
public class AudienceService {

    private AudienceDao audienceDao;

    public AudienceService(AudienceDao audienceDao) {
	this.audienceDao = audienceDao;
    }

    public Audience getAudienceById(int id) {
	return audienceDao.findById(id);
    }

}
