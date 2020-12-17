package ua.com.foxminded.krailo.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public String showAllLessons() {
	StringBuilder sb = new StringBuilder();
	sb.append(name).append(System.lineSeparator());
	sb.append("all lessons for ").append(speciality).append(" ").append(year).append(System.lineSeparator());
	String pattern = "%-10s| %-12s| %-15s| %-20s";
	for (Lesson lesson : lessons) {
	    sb.append(String.format(pattern, lesson.getDate(), lesson.getAudience(), lesson.getSubject(),
		    lesson.getLessonTime()));
	    sb.append(System.lineSeparator());
	}
	return sb.toString();
    }

}
