package ua.com.foxminded.krailo.university.domain;

import java.util.List;
import java.util.stream.Collectors;

import ua.com.foxminded.krailo.university.entities.Building;
import ua.com.foxminded.krailo.university.entities.Faculty;
import ua.com.foxminded.krailo.university.entities.Holiday;
import ua.com.foxminded.krailo.university.entities.UniversityOffice;

public class UniversityAdmin {

    private UniversityOffice office;

    public UniversityAdmin(UniversityOffice universityOffice) {
	this.office = universityOffice;
    }

    public String showHolidays() {
	office.setHolidays(null);
	StringBuilder sb = new StringBuilder();
	for (Holiday holiday : office.getHolidays()) {
	    sb.append(holiday.toString()).append(System.lineSeparator());
	}
	return sb.toString();
    }

    public Faculty getFacultyById(String id) {
	List<Faculty> facultyFiltered = office.getFaculties().stream().filter(f -> f.getId().equals(id))
		.collect(Collectors.toList());
	if (facultyFiltered.size() == 0) {
	    System.out.println("faculty with id " + id + " not exist");
	    return null;
	}
	return facultyFiltered.get(0);
    }
    
    public Building getBuildingById (String buildingId) {
	List<Building> buildings = office.getBuildings().stream().filter(b -> b.getId().equals(buildingId)).collect(Collectors.toList());
	if(buildings.size() == 0) {
	    System.out.println("building with id " + buildingId + "not exist");
	}
	return buildings.get(0);
    }

    public String showAllStudents() {
	return office.getFaculties().stream().flatMap(f -> f.getSpecialities().stream())
		.flatMap(s -> s.getYears().stream()).flatMap(s -> s.getStudents().stream()).map(s -> s.toString() + System.lineSeparator())
		.collect(Collectors.joining());
    }

}