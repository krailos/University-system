package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class Year {

    private int id;
    private String name;
    private Speciality speciality;
    private List<Subject> subjects = new ArrayList<>();
    private List<Student> students = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();

    public Year() {
    }

    public Year(int id, String name, Speciality speciality) {
	super();
	this.id = id;
	this.name = name;
	this.speciality = speciality;
    }

    public Year(String name, Speciality speciality) {
	this.name = name;
	this.speciality = speciality;
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

    public Speciality getSpeciality() {
	return speciality;
    }

    public void setSpeciality(Speciality speciality) {
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

    public List<Student> getStudents() {
	return students;
    }

    public void setStudents(List<Student> students) {
	this.students = students;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((groups == null) ? 0 : groups.hashCode());
	result = prime * result + id;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((speciality == null) ? 0 : speciality.hashCode());
	result = prime * result + ((students == null) ? 0 : students.hashCode());
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
	if (students == null) {
	    if (other.students != null)
		return false;
	} else if (!students.equals(other.students))
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
		+ ", students=" + students + ", groups=" + groups + "]";
    }

}
