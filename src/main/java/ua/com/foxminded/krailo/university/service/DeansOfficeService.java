package ua.com.foxminded.krailo.university.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.DeansOfficeDao;
import ua.com.foxminded.krailo.university.exception.ServiceException;
import ua.com.foxminded.krailo.university.model.DeansOffice;
import static java.lang.String.format;

@Service
public class DeansOfficeService {

    private static final Logger log = LoggerFactory.getLogger(DeansOfficeService.class);
    private DeansOfficeDao deansOfficeDao;

    public DeansOfficeService(DeansOfficeDao deansOfficeDao) {
	this.deansOfficeDao = deansOfficeDao;
    }

    public void create(DeansOffice deansOffice) {
	log.debug("Create deansOffice{}", deansOffice);
	deansOfficeDao.create(deansOffice);
    }

    public void update(DeansOffice deansOffice) {
	log.debug("Update deansOffice{}", deansOffice);
	deansOfficeDao.update(deansOffice);
    }

    public DeansOffice getById(int id) {
	log.debug("Get deansOffice by id={}", id);
	Optional<DeansOffice> existingDeansOffice = deansOfficeDao.findById(id);
	if (existingDeansOffice.isPresent()) {
	    return existingDeansOffice.get();
	} else {
	    throw new ServiceException(format("DeansOffice whith id=%s not exist", id));
	}
    }

    public List<DeansOffice> getAll() {
	log.debug("Get all deansOffices");
	return deansOfficeDao.findAll();
    }

    public void delete(DeansOffice deansOffice) {
	log.debug("Delete deansOffice={}", deansOffice);
	deansOfficeDao.deleteById(deansOffice.getId());
    }

}
