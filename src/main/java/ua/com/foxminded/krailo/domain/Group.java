package ua.com.foxminded.krailo.domain;

import java.util.List;

public class Group {

    public Group() {

    }

    public Group(String name) {
	this.name = name;

    }

    private String name;
    private Year year;
    private Speciality speciality;
    private List<Student> students;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
	return name + " " + year;
    }
    
    
    
}
