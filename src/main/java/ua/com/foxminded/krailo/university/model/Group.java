package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private int id;
    private String name;
    private Year year;
    private List<Student> students = new ArrayList<>();

    public Group() {
    }

    public Group(int id, String name, Year year, List<Student> students) {
	this.id = id;
	this.name = name;
	this.year = year;
	this.students = students;

    }

    public static GroupBuilder builder() {
	return new GroupBuilder();
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

    public Year getYear() {
	return year;
    }

    public void setYear(Year year) {
	this.year = year;
    }

    public List<Student> getStudents() {
	return students;
    }

    public void setStudents(List<Student> students) {
	this.students = students;
    }

    public static class GroupBuilder {

	private int id;
	private String name;
	private Year year;
	private List<Student> students = new ArrayList<>();

	public GroupBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public GroupBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public GroupBuilder year(Year year) {
	    this.year = year;
	    return this;
	}

	public GroupBuilder students(List<Student> students) {
	    this.students = students;
	    return this;
	}

	public Group build() {
	    return new Group(id, name, year, students);
	}

    }

    @Override
    public String toString() {
	return "id = " + id + " " + "name = " + name + " " + "year = " + year;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((students == null) ? 0 : students.hashCode());
	result = prime * result + ((year == null) ? 0 : year.hashCode());
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
	Group other = (Group) obj;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (students == null) {
	    if (other.students != null)
		return false;
	} else if (!students.equals(other.students))
	    return false;
	if (year == null) {
	    if (other.year != null)
		return false;
	} else if (!year.equals(other.year))
	    return false;
	return true;
    }

}
