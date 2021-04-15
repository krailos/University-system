package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class Subject {

    private int id;
    private String name;
    private String description;
    private List<Teacher> teachers = new ArrayList<>();

    public Subject() {
    }

    public Subject(int id, String name, String description, List<Teacher> teachers) {
	this.id = id;
	this.name = name;
	this.description = description;
	this.teachers = teachers;
    }

    public static SubjectBuilder builder() {
	return new SubjectBuilder();
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

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public List<Teacher> getTeachers() {
	return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
	this.teachers = teachers;
    }

    public static class SubjectBuilder {

	private int id;
	private String name;
	private String description;
	private List<Teacher> teachers = new ArrayList<>();

	public SubjectBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public SubjectBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public SubjectBuilder description(String description) {
	    this.description = description;
	    return this;
	}

	public SubjectBuilder teachers(List<Teacher> teachers) {
	    this.teachers = teachers;
	    return this;
	}

	public Subject build() {
	    return new Subject(id, name, description, teachers);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((teachers == null) ? 0 : teachers.hashCode());
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
	Subject other = (Subject) obj;
	if (id != other.id)
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (teachers == null) {
	    if (other.teachers != null)
		return false;
	} else if (!teachers.equals(other.teachers))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "id = " + id + " " + "name = " + name;
    }

}
