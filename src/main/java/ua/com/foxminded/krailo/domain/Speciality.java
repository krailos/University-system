package ua.com.foxminded.krailo.domain;

import java.util.List;

public class Speciality {

    private String name;
    private Faculty faculty;
    private List<Year> years;

    public Speciality() {
    }

    public Speciality(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Faculty getFaculty() {
	return faculty;
    }

    public void setFaculty(Faculty faculty) {
	this.faculty = faculty;
    }

    public List<Year> getYears() {
	return years;
    }

    public void setYears(List<Year> years) {
	this.years = years;
    }

    @Override
    public String toString() {
	return name;
    }
    


}
