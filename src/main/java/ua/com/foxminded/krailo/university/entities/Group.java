package ua.com.foxminded.krailo.university.entities;

import java.util.List;

public class Group {

    private String name;
    private Year year;
    private Speciality speciality;
    private List<Student> students;

    public Group() {
    }

    public Group(String name, Year year, Speciality speciality) {
	this.name = name;
	this.year = year;
	this.speciality = speciality;
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

    public Speciality getSpeciality() {
	return speciality;
    }

    public void setSpeciality(Speciality speciality) {
	this.speciality = speciality;
    }

    public List<Student> getStudents() {
	return students;
    }

    public void setStudents(List<Student> students) {
	this.students = students;
    }

    @Override
    public String toString() {
	return name + " " + year;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((speciality == null) ? 0 : speciality.hashCode());
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
	if (year == null) {
	    if (other.year != null)
		return false;
	} else if (!year.equals(other.year))
	    return false;
	return true;
    }

    public void addStudentToGroup(Student student) {
	student.setGroup(this);
	students.add(student);
    }

}
