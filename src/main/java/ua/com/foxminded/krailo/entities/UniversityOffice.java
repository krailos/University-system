package ua.com.foxminded.krailo.entities;

import java.util.List;

public class UniversityOffice {

    private String name;
    private String address;
    private List<Faculty> faculties;
    private List<Building> building;
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
    
    public List<Building> getBuilding() {
        return building;
    }

    public void setBuilding(List<Building> building) {
        this.building = building;
    }

    @Override
    public String toString() {
	return name;
    }
 

}
