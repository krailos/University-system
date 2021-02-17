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

    public static class StudentBuilder {

	private Student student;

	public StudentBuilder() {
	    student = new Student();
	}

	public StudentBuilder withId(int id) {
	    student.id = id;
	    return this;
	}

	public StudentBuilder withStudentId(String studentId) {
	    student.studentId = studentId;
	    return this;
	}

	public StudentBuilder withFirstName(String firstName) {
	    student.firstName = firstName;
	    return this;
	}

	public StudentBuilder withlastName(String lastName) {
	    student.lastName = lastName;
	    return this;
	}

	public StudentBuilder withBirthDate(LocalDate birthDate) {
	    student.birthDate = birthDate;
	    return this;
	}

	public StudentBuilder withPhone(String phone) {
	    student.phone = phone;
	    return this;
	}

	public StudentBuilder withAddress(String address) {
	    student.address = address;
	    return this;
	}

	public StudentBuilder withEmail(String email) {
	    student.email = email;
	    return this;
	}

	public StudentBuilder withRank(String rank) {
	    student.rank = rank;
	    return this;
	}

	public StudentBuilder withGender(Gender gender) {
	    student.gender = gender;
	    return this;
	}

	public StudentBuilder withGroup(Group group) {
	    student.group = group;
	    return this;
	}

	public Student built() {
	    return student;
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((address == null) ? 0 : address.hashCode());
	result = prime * result + ((birthDate == null) ? 0 : birthDate.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
	result = prime * result + ((gender == null) ? 0 : gender.hashCode());
	result = prime * result + ((group == null) ? 0 : group.hashCode());
	result = prime * result + id;
	result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
	result = prime * result + ((phone == null) ? 0 : phone.hashCode());
	result = prime * result + ((rank == null) ? 0 : rank.hashCode());
	result = prime * result + ((studentId == null) ? 0 : studentId.hashCode());
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
	Student other = (Student) obj;
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
	if (group == null) {
	    if (other.group != null)
		return false;
	} else if (!group.equals(other.group))
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
	if (rank == null) {
	    if (other.rank != null)
		return false;
	} else if (!rank.equals(other.rank))
	    return false;
	if (studentId == null) {
	    if (other.studentId != null)
		return false;
	} else if (!studentId.equals(other.studentId))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Student [id=" + studentId + ", firstName=" + firstName + ", lastName=" + lastName + ", birthDate="
		+ birthDate + ", group=" + group + ", phone=" + phone + ", address=" + address + ", email=" + email
		+ ", gender=" + gender + ", rank=" + rank + "]";
    }

}
