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

    public Timetable(int id) {
	this.id = id;
    }

    public Timetable(String name, Year year) {
	this.name = name;
	this.year = year;
    }

    public Timetable(String name, Year year, Speciality speciality) {
	this.name = name;
	this.year = year;
	this.speciality = speciality;
    }

    public Timetable(int id, String name, Year year) {
	this.id = id;
	this.name = name;
	this.year = year;
    }

    public Timetable(int id, String name, Year year, Speciality speciality) {
	this.id = id;
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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	result = prime * result + ((lessons == null) ? 0 : lessons.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((speciality == null) ? 0 : speciality.hashCode());
	result = prime * result + ((year == null) ? 0 : year.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Timetable other = (Timetable) obj;
	if (id != other.id)
	    return false;
	if (lessons == null) {
	    if (other.lessons != null)
		return false;
	} else if (!lessons.equals(other.lessons))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (speciality == null) {
	    if (other.speciality != null)
		return false;
	} else if (!speciality.equals(other.speciality))
	    return false;
	if (year == null) {
	    if (other.year != null)
		return false;
	} else if (!year.equals(other.year))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Timetable [id=" + id + ", name=" + name + ", year=" + year + ", speciality=" + speciality + ", lessons="
		+ lessons + "]";
    }

}
