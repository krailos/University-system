package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class Year {

    private int id;
    private String name;
    private Specialty speciality;
    private List<Subject> subjects = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();

    public Year() {
    }

    public Year(int id, String name, Specialty speciality, List<Subject> subjects, List<Group> groups) {
	this.id = id;
	this.name = name;
	this.speciality = speciality;
	this.subjects = subjects;
	this.groups = groups;
    }

    public static YearBuilder builder() {
	return new YearBuilder();
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

    public Specialty getSpeciality() {
	return speciality;
    }

    public void setSpeciality(Specialty speciality) {
	this.speciality = speciality;
    }

    public List<Subject> getSubjects() {
	return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
	this.subjects = subjects;
    }

    public List<Group> getGroups() {
	return groups;
    }

    public void setGroups(List<Group> groups) {
	this.groups = groups;
    }

    public static class YearBuilder {

	private int id;
	private String name;
	private Specialty speciality;
	private List<Subject> subjects = new ArrayList<>();
	private List<Group> groups = new ArrayList<>();

	public YearBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public YearBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public YearBuilder speciality(Specialty speciality) {
	    this.speciality = speciality;
	    return this;
	}

	public YearBuilder groups(List<Group> groups) {
	    this.groups = groups;
	    return this;
	}

	public YearBuilder subjects(List<Subject> subjects) {
	    this.subjects = subjects;
	    return this;
	}

	public Year build() {
	    return new Year(id, name, speciality, subjects, groups);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((groups == null) ? 0 : groups.hashCode());
	result = prime * result + id;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((speciality == null) ? 0 : speciality.hashCode());
	result = prime * result + ((subjects == null) ? 0 : subjects.hashCode());
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
	Year other = (Year) obj;
	if (groups == null) {
	    if (other.groups != null)
		return false;
	} else if (!groups.equals(other.groups))
	    return false;
	if (id != other.id)
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (speciality == null) {
	    if (other.speciality != null)
		return false;
	} else if (!speciality.equals(other.speciality))
	    return false;
	if (subjects == null) {
	    if (other.subjects != null)
		return false;
	} else if (!subjects.equals(other.subjects))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Year [id=" + id + ", name=" + name + ", speciality=" + speciality + ", subjects=" + subjects
		+ ", groups=" + groups + "]";
    }

}