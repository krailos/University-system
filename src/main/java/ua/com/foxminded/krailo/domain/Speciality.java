package ua.com.foxminded.krailo.domain;

import java.util.List;

public class Speciality {

    private String id;
    private String name;
    private Faculty faculty;
    private List<Year> years;

    public Speciality() {
    }

    public Speciality(String id, String name, Faculty faculty) {
	this.id = id;
	this.name = name;
	this.faculty = faculty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
