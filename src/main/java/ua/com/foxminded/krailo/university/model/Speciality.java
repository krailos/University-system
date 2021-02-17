package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class Speciality {

    private int id;
    private String name;
    private Faculty faculty;
    private List<Year> years = new ArrayList<>();

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

    public Faculty getFaculty() {
	return faculty;
    }

    public void setFaculty(Faculty faculty) {
	this.faculty = faculty;
    }

    public List<Year> getYears() {
	return years;
    }

    public void setYears(List<Year> years) {
	this.years = years;
    }

    public static class SpecialityBuilder {

	private Speciality speciality;

	public SpecialityBuilder() {
	    speciality = new Speciality();
	}

	public SpecialityBuilder withId(int id) {
	    speciality.id = id;
	    return this;
	}

	public SpecialityBuilder withName(String name) {
	    speciality.name = name;
	    return this;
	}

	public SpecialityBuilder withFaculty(Faculty faculty) {
	    speciality.faculty = faculty;
	    return this;
	}

	public SpecialityBuilder withYears(List<Year> years) {
	    speciality.years = years;
	    return this;
	}

	public Speciality built() {
	    return speciality;
	}

    }

    @Override
    public String toString() {
	return name;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((faculty == null) ? 0 : faculty.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((years == null) ? 0 : years.hashCode());
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
	Speciality other = (Speciality) obj;
	if (faculty == null) {
	    if (other.faculty != null)
		return false;
	} else if (!faculty.equals(other.faculty))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (years == null) {
	    if (other.years != null)
		return false;
	} else if (!years.equals(other.years))
	    return false;
	return true;
    }

}
