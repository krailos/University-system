package ua.com.foxminded.krailo.university.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.krailo.university.model.Holiday.HolidayBuilder;

public class Department {

    private int id;
    private String name;
    private List<Teacher> teachers = new ArrayList<>();

  

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

    public List<Teacher> getTeachers() {
	return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
	this.teachers = teachers;
    }
    
public static class DepartmentBuilder {
	
	private Department department;

	public DepartmentBuilder() {
	    department = new Department();
	}

	public DepartmentBuilder withId(int id) {
	    department.id = id;
	    return this;
	}

	public DepartmentBuilder withName(String name) {
	    department.name = name;
	    return this;
	}

	public DepartmentBuilder withTeachers(List<Teacher> teachers) {
	    department.teachers = teachers;
	    return this;
	}

	public Department built() {
	    return department;
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
	Department other = (Department) obj;
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
	return name;
    }

}
