package ua.com.foxminded.krailo.university.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class Teacher {

    private int id;
    private String teacherId;
    private String firstName;
    private String lastName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    private List<Subject> subjects = new ArrayList<>();
    private String phone;
    private String address;
    private String email;
    private Gender gender;
    private String degree;

    public Teacher() {
    }

    public Teacher(int id, String teacherId, String firstName, String lastName, LocalDate birthDate,
	    List<Subject> subjects, String phone, String address, String email, Gender gender, String degree) {
	this.id = id;
	this.teacherId = teacherId;
	this.firstName = firstName;
	this.lastName = lastName;
	this.birthDate = birthDate;
	this.subjects = subjects;
	this.phone = phone;
	this.address = address;
	this.email = email;
	this.gender = gender;
	this.degree = degree;
    }

    public static TeacherBuilder builder() {
	return new TeacherBuilder();
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

    public static class TeacherBuilder {

	private int id;
	private String teacherId;
	private String firstName;
	private String lastName;
	private LocalDate birthDate;
	private List<Subject> subjects = new ArrayList<>();
	private String phone;
	private String address;
	private String email;
	private Gender gender;
	private String degree;

	public TeacherBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public TeacherBuilder teacherId(String teacherId) {
	    this.teacherId = teacherId;
	    return this;
	}

	public TeacherBuilder firstName(String firstName) {
	    this.firstName = firstName;
	    return this;
	}

	public TeacherBuilder lastName(String lastName) {
	    this.lastName = lastName;
	    return this;
	}

	public TeacherBuilder birthDate(LocalDate birthDate) {
	    this.birthDate = birthDate;
	    return this;
	}

	public TeacherBuilder phone(String phone) {
	    this.phone = phone;
	    return this;
	}

	public TeacherBuilder address(String address) {
	    this.address = address;
	    return this;
	}

	public TeacherBuilder email(String email) {
	    this.email = email;
	    return this;
	}

	public TeacherBuilder degree(String degree) {
	    this.degree = degree;
	    return this;
	}

	public TeacherBuilder gender(Gender gender) {
	    this.gender = gender;
	    return this;
	}
	public TeacherBuilder subjects (List<Subject> subjects) {
	    this.subjects = subjects;
	    return this;
	}

	public Teacher build() {
	    return new Teacher(id, teacherId, firstName, lastName, birthDate, subjects, phone, address, email, gender,
		    degree);
	}

    }

    @Override
    public String toString() {
	return "Teacher [id=" + id + ", teacherId=" + teacherId + ", firstName=" + firstName + ", lastName=" + lastName
		+ ", birthDate=" + birthDate  + ", subjects=" + subjects + ", phone="
		+ phone + ", address=" + address + ", email=" + email + ", gender=" + gender + ", degree=" + degree
		+ "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((address == null) ? 0 : address.hashCode());
	result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
	result = prime * result + ((degree == null) ? 0 : degree.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
	result = prime * result + ((gender == null) ? 0 : gender.hashCode());
	result = prime * result + id;
	result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
	result = prime * result + ((phone == null) ? 0 : phone.hashCode());
	result = prime * result + ((subjects == null) ? 0 : subjects.hashCode());
	result = prime * result + ((teacherId == null) ? 0 : teacherId.hashCode());
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
	if (address == null) {
	    if (other.address != null)
		return false;
	} else if (!address.equals(other.address))
	    return false;
	if (birthDate == null) {
	    if (other.birthDate != null)
		return false;
	} else if (!birthDate.equals(other.birthDate))
	    return false;
	if (degree == null) {
	    if (other.degree != null)
		return false;
	} else if (!degree.equals(other.degree))
	    return false;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (firstName == null) {
	    if (other.firstName != null)
		return false;
	} else if (!firstName.equals(other.firstName))
	    return false;
	if (gender != other.gender)
	    return false;
	if (id != other.id)
	    return false;
	if (lastName == null) {
	    if (other.lastName != null)
		return false;
	} else if (!lastName.equals(other.lastName))
	    return false;
	if (phone == null) {
	    if (other.phone != null)
		return false;
	} else if (!phone.equals(other.phone))
	    return false;
	if (subjects == null) {
	    if (other.subjects != null)
		return false;
	} else if (!subjects.equals(other.subjects))
	    return false;
	if (teacherId == null) {
	    if (other.teacherId != null)
		return false;
	} else if (!teacherId.equals(other.teacherId))
	    return false;
	return true;
    }

}
