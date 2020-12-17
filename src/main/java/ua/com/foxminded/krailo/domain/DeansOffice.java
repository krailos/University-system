package ua.com.foxminded.krailo.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class DeansOffice {

    private String name;
    private Faculty faculty;
    private UniversityOffice universityOffice;
    private List<Timetable> timetables;
    private List<Vocation> vocations;

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

    @Override
    public String toString() {
	return name;
    }

    public String showAllTimetables() {
	StringBuilder sb = new StringBuilder();
	sb.append(name).append(System.lineSeparator());
	String allTimetables = timetables.stream().map(t -> t.showAllLessons()).collect(Collectors.joining());
	sb.append(allTimetables).append(System.lineSeparator());
	return sb.toString();
    }
    
    public Timetable getTimetableBySpecialityAndYear(String specialityName, String yearName) {
	Timetable timetable = timetables.stream().filter(t -> t.getSpeciality().getName().equals(specialityName) && t.getYear().getName().equals(yearName)).
	collect(Collectors.toList()).get(0);
	if (timetable == null) {
	System.out.println("timetable for speciality " + specialityName + " and year " + yearName + " not exist");
	return null;
	}
	return timetable;
    }

    public String showVocationByTeacher(Teacher teacher, LocalDate start, LocalDate end) {
	StringBuilder sb = new StringBuilder();
	sb.append("Vocations").append(System.lineSeparator());
	sb.append(teacher.getFirstName()).append(" ").append(teacher.getLastName()).append(System.lineSeparator());
	sb.append("Period from " + start.toString()).append(" to " + end.toString()).append(System.lineSeparator());
	String pattern = "%-10s| %-12s| %-12s";

	List<Vocation> vocationFiltered = vocations.stream().filter(v -> v.getTeacher().equals(teacher)
		&& v.getStartVocation().isAfter(start.minusDays(1)) && v.getStartVocation().isBefore(end.plusDays(1)))
		.collect(Collectors.toList());
	for (Vocation vocation : vocationFiltered) {
	    sb.append(
		    String.format(pattern, vocation.getKind(), vocation.getStartVocation(), vocation.getEndVocation()));
	    sb.append(System.lineSeparator());
	}
	return sb.toString();
    }

}
