package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private int id;
    private String name;
    private Year year;
    private List<Student> students = new ArrayList<>();

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

	private Group group;

	public GroupBuilder() {
	    group = new Group();
	}

	public GroupBuilder withId(int id) {
	    group.id = id;
	    return this;
	}

	public GroupBuilder withName(String name) {
	    group.name = name;
	    return this;
	}

	public GroupBuilder withYear(Year year) {
	    group.year = year;
	    return this;
	}

	public Group built() {
	    return group;
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
