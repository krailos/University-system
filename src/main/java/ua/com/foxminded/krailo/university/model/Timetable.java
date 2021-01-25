package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class Timetable {

    private int id;
    private String name;
    private Year year;
    private Speciality speciality;
    private List<Lesson> lessons = new ArrayList<>();

    public Timetable() {
    }

    public Timetable(int id, String name, Year year, Speciality speciality) {
	super();
	this.id = id;
	this.name = name;
	this.year = year;
	this.speciality = speciality;
    }

    public Timetable(String name, Year year, Speciality speciality) {
	super();
	this.name = name;
	this.year = year;
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

    public Year getYear() {
	return year;
    }

    public void setYear(Year year) {
	this.year = year;
    }

    public List<Lesson> getLessons() {
	return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
	this.lessons = lessons;
    }

}
