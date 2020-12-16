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

    public String showTimetableByStudent(Student student, LocalDate start, LocalDate end) {
	StringBuilder sb = new StringBuilder();
	sb.append(name).append(System.lineSeparator());
	sb.append(student.getSpeciality()).append(" ").append(student.getGroup().getYear())
		.append(System.lineSeparator());
	String pattern = "%-10s| %-12s| %-15s| %-20s";

	List<Lesson> lessonFiltered = lessons.stream().filter(l -> l.getDate().isAfter(start.minusDays(1))
		&& l.getDate().isBefore(end.plusDays(1)) && l.getGroups().contains(student.getGroup()))
		.collect(Collectors.toList());
	for (Lesson lesson : lessonFiltered) {
	    sb.append(String.format(pattern, lesson.getDate().toString(), lesson.getAudience(), lesson.getSubject(),
		    lesson.getLessonTime()));
	    sb.append(System.lineSeparator());
	}
	return sb.toString();
    }

    public String showTimetableByTeacher(Teacher teacher, LocalDate start, LocalDate end) {
	StringBuilder sb = new StringBuilder();
	sb.append(name).append(System.lineSeparator());
	sb.append(teacher.getFirstName()).append(" ").append(teacher.getLastName())
		.append(System.lineSeparator());
	String pattern = "%-10s| %-12s| %-15s| %-20s";

	List<Lesson> lessonFiltered = lessons.stream().filter(l -> l.getDate().isAfter(start.minusDays(1))
		&& l.getDate().isBefore(end.plusDays(1)) && l.getTeacher().equals(teacher))
		.collect(Collectors.toList());
	for (Lesson lesson : lessonFiltered) {
	    sb.append(String.format(pattern, lesson.getDate().toString(), lesson.getAudience(), lesson.getSubject(),
		    lesson.getLessonTime()));
	    sb.append(System.lineSeparator());
	}
	return sb.toString();
    }

}
