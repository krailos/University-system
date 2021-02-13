package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.FacultyDao;
import ua.com.foxminded.krailo.university.dao.SpecialityDao;
import ua.com.foxminded.krailo.university.model.Faculty;

@Service
public class FacultyService {

    private FacultyDao facultyDao;
    private SpecialityDao specialityDao;

    public FacultyService(FacultyDao facultyDao, SpecialityDao specialityDao) {
	this.facultyDao = facultyDao;
	this.specialityDao = specialityDao;
    }

    public void create(Faculty faculty) {
	facultyDao.create(faculty);
    }

    public void update(Faculty faculty) {
	facultyDao.update(faculty);
    }

    public Faculty getById(int id) {
	Faculty faculty = facultyDao.findById(id);
	addSpecialitiesToFaculty(faculty);
	return faculty;
    }

    public List<Faculty> getAll() {
	List<Faculty> faculties = facultyDao.findAll();
	for (Faculty faculty : faculties) {
	    addSpecialitiesToFaculty(faculty);
	}
	return faculties;
    }

    public void delete(Faculty faculty) {
	facultyDao.deleteById(faculty.getId());
    }

    private void addSpecialitiesToFaculty(Faculty faculty) {
	faculty.setSpecialities(specialityDao.findByFacultyId(faculty.getId()));
    }

}
