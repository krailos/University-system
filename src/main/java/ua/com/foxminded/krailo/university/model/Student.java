package ua.com.foxminded.krailo.university.model;

import java.time.LocalDate;

public class Student {

    private int id;
    private String studentId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phone;
    private String address;
    private String email;
    private String rank;
    private Gender gender;
    private Group group;

    public Student() {
    }

    public Student(int id, String studentId, String firstName, String lastName, LocalDate birthDate, String phone,
	    String address, String email, String rank, Gender gender, Group group) {
	super();
	this.id = id;
	this.studentId = studentId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.birthDate = birthDate;
	this.phone = phone;
	this.address = address;
	this.email = email;
	this.rank = rank;
	this.gender = gender;
	this.group = group;
    }

    public Student(String studentId, String firstName, String lastName, LocalDate birthDate, String phone,
	    String address, String email, String rank, Gender gender, Group group) {
	this.studentId = studentId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.birthDate = birthDate;
	this.phone = phone;
	this.address = address;
	this.email = email;
	this.rank = rank;
	this.gender = gender;
	this.group = group;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getStudentId() {
	return studentId;
    }

    public void setStudentId(String id) {
	this.studentId = id;
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

    public Gender getGender() {
	return gender;
    }

    public void setGender(Gender gender) {
	this.gender = gender;
    }

    public String getRank() {
	return rank;
    }

    public void setRank(String rank) {
	this.rank = rank;
    }

    @Override
    public String toString() {
	return "Student [id=" + studentId + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate="
		+ birthDate + ", group=" + group + ", phone=" + phone + ", address=" + address + ", email=" + email
		+ ", gender=" + gender + ", rank=" + rank + "]";
    }

}
