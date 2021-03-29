package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class Specialty {

    private int id;
    private String name;
    private Faculty faculty;
    private List<Year> years = new ArrayList<>();

    public Specialty() {
    }

    public Specialty(int id, String name, Faculty faculty, List<Year> years) {
	this.id = id;
	this.name = name;
	this.faculty = faculty;
	this.years = years;
    }

    public static SpecialityBuilder builder() {
	return new SpecialityBuilder();
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

	private int id;
	private String name;
	private Faculty faculty;
	private List<Year> years = new ArrayList<>();

	public SpecialityBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public SpecialityBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public SpecialityBuilder faculty(Faculty faculty) {
	    this.faculty = faculty;
	    return this;
	}

	public SpecialityBuilder years(List<Year> years) {
	    this.years = years;
	    return this;
	}

	public Specialty build() {
	    return new Specialty(id, name, faculty, years);
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
	Specialty other = (Specialty) obj;
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
