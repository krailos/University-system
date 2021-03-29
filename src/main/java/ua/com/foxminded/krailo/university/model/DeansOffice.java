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

    public DeansOffice(int id, String name, UniversityOffice universityOffice) {
	this.id = id;
	this.name = name;
	this.universityOffice = universityOffice;
    }

    public static DeansOfficeBuilder builder() {
	return new DeansOfficeBuilder();
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

    public static class DeansOfficeBuilder {

	private int id;
	private String name;
	private UniversityOffice universityOffice;

	public DeansOfficeBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public DeansOfficeBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public DeansOfficeBuilder universityOffice(UniversityOffice universityOffice) {
	    this.universityOffice = universityOffice;
	    return this;
	}

	public DeansOffice build() {
	    return new DeansOffice(id, name, universityOffice);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((faculties == null) ? 0 : faculties.hashCode());
	result = prime * result + id;
	result = prime * result + ((lessonsTimeSchedules == null) ? 0 : lessonsTimeSchedules.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((timetables == null) ? 0 : timetables.hashCode());
	result = prime * result + ((universityOffice == null) ? 0 : universityOffice.hashCode());
	result = prime * result + ((vocations == null) ? 0 : vocations.hashCode());
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
	DeansOffice other = (DeansOffice) obj;
	if (faculties == null) {
	    if (other.faculties != null)
		return false;
	} else if (!faculties.equals(other.faculties))
	    return false;
	if (id != other.id)
	    return false;
	if (lessonsTimeSchedules == null) {
	    if (other.lessonsTimeSchedules != null)
		return false;
	} else if (!lessonsTimeSchedules.equals(other.lessonsTimeSchedules))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (timetables == null) {
	    if (other.timetables != null)
		return false;
	} else if (!timetables.equals(other.timetables))
	    return false;
	if (universityOffice == null) {
	    if (other.universityOffice != null)
		return false;
	} else if (!universityOffice.equals(other.universityOffice))
	    return false;
	if (vocations == null) {
	    if (other.vocations != null)
		return false;
	} else if (!vocations.equals(other.vocations))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return name;
    }

}
