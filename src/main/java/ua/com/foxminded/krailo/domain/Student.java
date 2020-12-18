package ua.com.foxminded.krailo.domain;

import java.util.Date;

public class Student {

    private String id;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Faculty faculty;
    private Speciality speciality;
    private Group group;
    private String phone;
    private String address;
    private String email;
    private Enum<Gender> gender;
    private Double rank;

    public Student() {
    }

    public Student(String id, String firstName, String lastName, Faculty faculty, Speciality speciality) {
	super();
	this.id = id;
	this.firstName = firstName;
	this.lastName = lastName;
	this.faculty = faculty;
	this.speciality = speciality;
	this.group = group;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
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

    public Date getBirthDate() {
	return birthDate;
    }

    public void setBirthDate(Date birthDate) {
	this.birthDate = birthDate;
    }

    public Faculty getFaculty() {
	return faculty;
    }

    public void setFaculty(Faculty faculty) {
	this.faculty = faculty;
    }

    public Speciality getSpeciality() {
	return speciality;
    }

    public void setSpeciality(Speciality speciality) {
	this.speciality = speciality;
    }

    public Group getGroup() {
	return group;
    }

    public void setGroup(Group group) {
	this.group = group;
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

    public Enum<Gender> getGender() {
	return gender;
    }

    public void setGender(Enum<Gender> gender) {
	this.gender = gender;
    }

    public Double getRank() {
	return rank;
    }

    public void setRank(Double rank) {
	this.rank = rank;
    }

    @Override
    public String toString() {
	return id + " " + firstName + " " + lastName;
    }

}
