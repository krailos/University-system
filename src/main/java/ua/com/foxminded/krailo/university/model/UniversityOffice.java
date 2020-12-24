package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class UniversityOffice {

    private String name;
    private String address;
    private List<Faculty> faculties = new ArrayList<>();
    private List<Building> buildings = new ArrayList<>();
    private List<Holiday> holidays = new ArrayList<>();

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

    public List<Building> getBuildings() {
	return buildings;
    }

    public void setBuildings(List<Building> building) {
	this.buildings = building;
    }

    @Override
    public String toString() {
	return name;
    }

}
