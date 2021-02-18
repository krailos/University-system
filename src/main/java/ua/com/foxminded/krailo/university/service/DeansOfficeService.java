package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.DeansOfficeDao;
import ua.com.foxminded.krailo.university.model.DeansOffice;

@Service
public class DeansOfficeService {

    private DeansOfficeDao deansOfficeDao;

    public DeansOfficeService(DeansOfficeDao deansOfficeDao) {
	this.deansOfficeDao = deansOfficeDao;
    }

    public void create(DeansOffice deansOffice) {
	deansOfficeDao.create(deansOffice);
    }

    public void update(DeansOffice deansOffice) {
	deansOfficeDao.update(deansOffice);
    }

    public DeansOffice getById(int id) {
	return deansOfficeDao.findById(id);
    }

    public List<DeansOffice> getAll() {
	return deansOfficeDao.findAll();
    }

    public void delete(DeansOffice deansOffice) {
	deansOfficeDao.deleteById(deansOffice.getId());
    }

}
