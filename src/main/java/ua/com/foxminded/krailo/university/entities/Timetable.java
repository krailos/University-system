package ua.com.foxminded.krailo.university.entities;

import java.util.List;

public class Timetable {

    private String name;
    private Speciality speciality;
    private Year year;
    private List<Lesson> lessons;

    public Timetable() {
    }

    public Timetable(String name, Speciality speciality, Year year) {
	this.name = name;
	this.speciality = speciality;
	this.year = year;
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
