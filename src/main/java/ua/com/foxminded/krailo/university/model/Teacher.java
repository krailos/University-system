package ua.com.foxminded.krailo.university.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Teacher {

    private int id;
    private String teacherId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Department department;
    private List<Subject> subjects = new ArrayList<>();
    private String phone;
    private String address;
    private String email;
    private Gender gender;
    private String degree;

    public Teacher() {
    }

    public Teacher(int id) {
	this.id = id;
    }

    public Teacher(int id, String teacherId, String firstName, String lastName, LocalDate birthDate, String phone,
	    String address, String email, String degree, Gender gender, Department department) {
	this.id = id;
	this.teacherId = teacherId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.birthDate = birthDate;
	this.phone = phone;
	this.address = address;
	this.email = email;
	this.degree = degree;
	this.gender = gender;
	this.department = department;
    }

    public Teacher(String teacherId, String firstName, String lastName, LocalDate birthDate, String phone,
	    String address, String email, String degree, Gender gender, Department department) {
	super();
	this.teacherId = teacherId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.birthDate = birthDate;

	this.phone = phone;
	this.address = address;
	this.email = email;

	this.degree = degree;
	this.gender = gender;
	this.department = department;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public List<Subject> getSubjects() {
	return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
	this.subjects = subjects;
    }

    public String getTeacherId() {
	return teacherId;
    }

    public void setTeacherId(String id) {
	this.teacherId = id;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
	return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
	this.birthDate = birthDate;
    }

    public Department getDepartment() {
	return department;
    }

    public void setDepartment(Department department) {
	this.department = department;
    }

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public Gender getGender() {
	return gender;
    }

    public void setGender(Gender gender) {
	this.gender = gender;
    }

    public String getDegree() {
	return degree;
    }

    public void setDegree(String degree) {
	this.degree = degree;
    }

    @Override
    public String toString() {
	return "Teacher [id=" + id + ", teacherId=" + teacherId + ", firstName=" + firstName + ", lastName=" + lastName
		+ ", birthDate=" + birthDate + ", department=" + department + ", subjects=" + subjects + ", phone="
		+ phone + ", address=" + address + ", email=" + email + ", gender=" + gender + ", degree=" + degree
		+ "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
	result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
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
	Teacher other = (Teacher) obj;
	if (firstName == null) {
	    if (other.firstName != null)
		return false;
	} else if (!firstName.equals(other.firstName))
	    return false;
	if (lastName == null) {
	    if (other.lastName != null)
		return false;
	} else if (!lastName.equals(other.lastName))
	    return false;
	return true;
    }

}
