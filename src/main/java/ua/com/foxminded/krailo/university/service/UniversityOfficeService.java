package ua.com.foxminded.krailo.university.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.com.foxminded.krailo.university.dao.BuildingDao;
import ua.com.foxminded.krailo.university.dao.DeansOfficeDao;
import ua.com.foxminded.krailo.university.dao.HolidayDao;
import ua.com.foxminded.krailo.university.dao.UniversityOfficeDao;
import ua.com.foxminded.krailo.university.model.UniversityOffice;

@Service
public class UniversityOfficeService {

    private UniversityOfficeDao universityOfficeDao;
    private DeansOfficeDao deansOfficeDao;
    private HolidayDao holidayDao;
    private BuildingDao buildingDao;

    public UniversityOfficeService(UniversityOfficeDao universityOfficeDao, DeansOfficeDao deansOfficeDao,
	    HolidayDao holidayDao, BuildingDao buildingDao) {
	this.universityOfficeDao = universityOfficeDao;
	this.deansOfficeDao = deansOfficeDao;
	this.holidayDao = holidayDao;
	this.buildingDao = buildingDao;
    }

    public void create(UniversityOffice universityOffice) {
	universityOfficeDao.create(universityOffice);
    }

    public void update(UniversityOffice universityOffice) {
	universityOfficeDao.update(universityOffice);
    }

    public UniversityOffice getById(int id) {
	UniversityOffice universityOffice = universityOfficeDao.findById(id);
	addDeansFacultyHolidaysBuildingsToUniversityOffice(universityOffice);
	return universityOffice;
    }

    public List<UniversityOffice> getAll() {
	List<UniversityOffice> universityOffices = universityOfficeDao.findAll();
	for (UniversityOffice universityOffice : universityOffices) {
	    addDeansFacultyHolidaysBuildingsToUniversityOffice(universityOffice);
	}
	return universityOffices;
    }

    public void delete(UniversityOffice universityOffice) {
	universityOfficeDao.deleteById(universityOffice.getId());
    }

    private void addDeansFacultyHolidaysBuildingsToUniversityOffice(UniversityOffice universityOffice) {
	universityOffice.setDeansOffices(deansOfficeDao.findAll());
	universityOffice.setHolidays(holidayDao.findAll());
	universityOffice.setBuildings(buildingDao.findAll());
    }

}
