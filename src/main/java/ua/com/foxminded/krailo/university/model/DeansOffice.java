package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class DeansOffice {

    private String name;
    private Faculty faculty;
    private UniversityOffice universityOffice;
    private List<Timetable> timetables = new ArrayList<>();
    private LessonsTimeSchedule lessonsTimeSchedule;
    private List<Vocation> vocations = new ArrayList<>();

    public DeansOffice() {
    }

    public DeansOffice(String name, Faculty faculty, UniversityOffice universityOffice) {
	this.name = name;
	this.faculty = faculty;
	this.universityOffice = universityOffice;
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

    public LessonsTimeSchedule getLessonsTimeSchedule() {
	return lessonsTimeSchedule;
    }

    public void setLessonsTimeSchedule(LessonsTimeSchedule lessonsTimeSchedule) {
	this.lessonsTimeSchedule = lessonsTimeSchedule;
    }

    @Override
    public String toString() {
	return name;
    }

}
