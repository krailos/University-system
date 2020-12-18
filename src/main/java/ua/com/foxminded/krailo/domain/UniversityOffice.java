package ua.com.foxminded.krailo.domain;

import java.util.List;
import java.util.stream.Collectors;

public class UniversityOffice {

    private String name;
    private String address;
    private List<Faculty> faculties;
    private List<Holiday> holidays;

    public UniversityOffice() {
    }

    public UniversityOffice(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public List<Faculty> getFaculties() {
	return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
	this.faculties = faculties;
    }

    public List<Holiday> getHolidays() {
	return holidays;
    }

    public void setHolidays(List<Holiday> holidays) {
	this.holidays = holidays;
    }

    @Override
    public String toString() {
	return name;
    }

    public String showHolidays() {
	StringBuilder sb = new StringBuilder();
	for (Holiday holiday : holidays) {
	    sb.append(holiday.toString()).append(System.lineSeparator());
	}
	return sb.toString();
    }

    public Faculty getFacultyById(String id) {
	List<Faculty> facultyFiltered = faculties.stream().filter(f -> f.getId().equals(id))
		.collect(Collectors.toList());
	if (facultyFiltered.size() == 0) {
	    System.out.println("faculty with id " + id + " not exist");
	    return null;
	}
	return facultyFiltered.get(0);
    }

    public Speciality getSpecialityById(String id) {
	List<Speciality> specialityFiltered = faculties.stream().flatMap(f -> f.getSpecialities().stream())
		.filter(s -> s.getId().equals(id)).collect(Collectors.toList());
	if (specialityFiltered.size() == 0) {
	    System.out.println("speciality with id " + id + " not exist");
	    return null;
	}
	return specialityFiltered.get(0);
    }

    public Group getGroupByName(String name) {
	List<Group> groupFiltered = faculties.stream().flatMap(f -> f.getSpecialities().stream())
		.flatMap(s -> s.getYears().stream()).flatMap(y -> y.getGroups().stream())
		.filter(g -> g.getName().equals(name)).collect(Collectors.toList());
	if (groupFiltered.size() == 0) {
	    System.out.println("group with name " + name + " not exist");
	    return null;
	}
	return groupFiltered.get(0);
    }

}
