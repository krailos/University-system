package ua.com.foxminded.krailo.domain;

import java.util.List;

public class Faculty {

    private String name;
    private List<Department> departments;
    private List<Speciality> specialities;
    private DeansOffice deansOffice;

    public Faculty() {
    }

    public Faculty(String name) {
	this.name = name;
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
