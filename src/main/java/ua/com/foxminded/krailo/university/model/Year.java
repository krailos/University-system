package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class Year {

    private int id;
    private String name;
    private Speciality speciality;
    private List<Subject> subjects = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();

    public Year() {
    }

    public Year(String name, Speciality speciality) {
	this.name = name;
	this.speciality = speciality;
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

    public List<Student> getStudents() {
	return students;
    }

    public void setStudents(List<Student> students) {
	this.students = students;
    }

    @Override
    public String toString() {
	return name;
    }

}
