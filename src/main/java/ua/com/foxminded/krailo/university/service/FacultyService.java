package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.FacultyDao;
import ua.com.foxminded.krailo.university.model.Faculty;

@Service
public class FacultyService {

    private FacultyDao facultyDao;

    public FacultyService(FacultyDao facultyDao) {
	this.facultyDao = facultyDao;
    }

    public void create(Faculty faculty) {
	facultyDao.create(faculty);
    }

    public void update(Faculty faculty) {
	facultyDao.update(faculty);
    }

    public Faculty getById(int id) {
	return facultyDao.findById(id);
    }

    public List<Faculty> getAll() {
	return facultyDao.findAll();
    }

    public void delete(Faculty faculty) {
	facultyDao.deleteById(faculty.getId());
    }

}
