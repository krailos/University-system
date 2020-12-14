package ua.com.foxminded.krailo.domain;

import java.util.List;

public class DeansOffice {

    private String name;
    private Faculty faculty;
    private List<Timetable> timetables;
    private List<Vocation> vocations;
    private UniversityOffice universityOffice;

    public DeansOffice() {
    }

    public DeansOffice(String name) {
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

    public List<Timetable> getTimetables() {
	return timetables;
    }

    public void setTimetables(List<Timetable> timetables) {
	this.timetables = timetables;
    }

    public List<Vocation> getVocations() {
	return vocations;
    }

    public void setVocations(List<Vocation> vocations) {
	this.vocations = vocations;
    }

    public UniversityOffice getUniversityOffice() {
	return universityOffice;
    }

    public void setUniversityOffice(UniversityOffice universityOffice) {
	this.universityOffice = universityOffice;
    }

    @Override
    public String toString() {
	return name;
    }
    
    

}
