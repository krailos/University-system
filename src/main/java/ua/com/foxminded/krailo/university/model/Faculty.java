package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class Faculty {

    private int id;
    private String name;
    private List<Department> departments = new ArrayList<>();
    private List<Speciality> specialities = new ArrayList<>();
    private DeansOffice deansOffice;

    public Faculty() {
    }

    public Faculty(int id, String name, DeansOffice deansOffice) {
	this.id = id;
	this.name = name;
	this.deansOffice = deansOffice;
    }

    public Faculty(String name, DeansOffice deansOffice) {
	this.name = name;
	this.deansOffice = deansOffice;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public List<Department> getDepartments() {
	return departments;
    }

    public void setDepartments(List<Department> departments) {
	this.departments = departments;
    }

    public List<Speciality> getSpecialities() {
	return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
	this.specialities = specialities;
    }

    public DeansOffice getDeansOffice() {
	return deansOffice;
    }

    public void setDeansOffice(DeansOffice deansOffice) {
	this.deansOffice = deansOffice;
    }

    @Override
    public String toString() {
	return name;
    }

}
