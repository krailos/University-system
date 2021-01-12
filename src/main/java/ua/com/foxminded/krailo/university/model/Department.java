package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class Department {

    private int id;
    private String name;
    private List<Teacher> teachers = new ArrayList<>();

    public Department() {

    }

    public Department(String name) {
	this.name = name;
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

    public List<Teacher> getTeachers() {
	return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
	this.teachers = teachers;
    }

    @Override
    public String toString() {
	return name;
    }

}
