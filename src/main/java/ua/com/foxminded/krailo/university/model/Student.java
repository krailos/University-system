package ua.com.foxminded.krailo.university.model;

import java.time.LocalDate;

public class Student {

    private int id;
    private String studentId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Faculty faculty;
    private Speciality speciality;
    private Group group;
    private String phone;
    private String address;
    private String email;
    private Gender gender;
    private String rank;

    public Student() {
    }

    public Student(String studentsId, String firstName, String lastName, Faculty faculty, Speciality speciality) {
	this.studentId = studentsId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.faculty = faculty;
	this.speciality = speciality;
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
		+ birthDate + ", faculty=" + faculty + ", speciality=" + speciality + ", group=" + group + ", phone="
		+ phone + ", address=" + address + ", email=" + email + ", gender=" + gender + ", rank=" + rank + "]";
    }

}
