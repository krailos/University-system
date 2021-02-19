package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.UniversityOfficeDao;
import ua.com.foxminded.krailo.university.model.UniversityOffice;

@Service
public class UniversityOfficeService {

    private UniversityOfficeDao universityOfficeDao;

    public UniversityOfficeService(UniversityOfficeDao universityOfficeDao) {
	this.universityOfficeDao = universityOfficeDao;
    }

    public void create(UniversityOffice universityOffice) {
	universityOfficeDao.create(universityOffice);
    }

    public void update(UniversityOffice universityOffice) {
	universityOfficeDao.update(universityOffice);
    }

    public UniversityOffice getById(int id) {
	return universityOfficeDao.findById(id);

    }

    public List<UniversityOffice> getAll() {
	return universityOfficeDao.findAll();
    }

    public void delete(UniversityOffice universityOffice) {
	universityOfficeDao.deleteById(universityOffice.getId());
    }

}
