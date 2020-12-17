package ua.com.foxminded.krailo.domain;

import java.util.List;

public class Year {

    private String name;
    private Speciality speciality;
    private List<Subject> subjects;
    private List<Group> groups;

    public Year() {
    }

    public Year(String name, Speciality speciality) {
	this.name = name;
	this.speciality = speciality;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Speciality getSpeciality() {
	return speciality;
    }

    public void setSpeciality(Speciality speciality) {
	this.speciality = speciality;
    }

    public List<Subject> getSubjects() {
	return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
	this.subjects = subjects;
    }

    public List<Group> getGroups() {
	return groups;
    }

    public void setGroups(List<Group> groups) {
	this.groups = groups;
    }

    @Override
    public String toString() {
	return name;
    }

}
