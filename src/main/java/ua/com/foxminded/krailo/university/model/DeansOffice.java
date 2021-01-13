package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class DeansOffice {

    private int id;
    private String name;
    private UniversityOffice universityOffice;
    private List<Faculty> faculties;
    private List<Timetable> timetables = new ArrayList<>();
    private List<LessonsTimeSchedule> lessonsTimeSchedules;
    private List<Vocation> vocations = new ArrayList<>();

    public DeansOffice() {
    }

    public DeansOffice(String name, Faculty faculty, UniversityOffice universityOffice) {
	this.name = name;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public List<Faculty> getFaculties() {
	return faculties;
    }

    public void setFaculties(List<Faculty> faculties) {
	this.faculties = faculties;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
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

    public List<LessonsTimeSchedule> getLessonsTimeSchedules() {
	return lessonsTimeSchedules;
    }

    public void setLessonsTimeSchedules(List<LessonsTimeSchedule> lessonsTimeSchedules) {
	this.lessonsTimeSchedules = lessonsTimeSchedules;
    }

    @Override
    public String toString() {
	return name;
    }

}
