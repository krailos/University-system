package ua.com.foxminded.krailo.domain;

import java.util.List;

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

}
