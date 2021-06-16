package ua.com.foxminded.krailo.university.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "students")
@NamedQueries({
    @NamedQuery(name = "SelectStudentsByGroup", query = "from Student s inner join s.group as g  where  g.id = :groupId"),
    @NamedQuery(name = "SelectAllStudents", query = "from Student s order by s.lastName"),
    @NamedQuery(name = "CountAllStudents",query = "select count(id) from Student")
})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "student_id")
    private String studentId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birth_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;
    private String phone;
    private String address;
    private String email;
    private String rank;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @ManyToOne
    private Group group;

    public Student() {
    }

    public Student(int id, String studentId, String firstName, String lastName, LocalDate birthDate, String phone,
	    String address, String email, String rank, Gender gender, Group group) {
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

    public static StudentBuilder builder() {
	return new StudentBuilder();
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

    public static class StudentBuilder {

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

	public StudentBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public StudentBuilder studentId(String studentId) {
	    this.studentId = studentId;
	    return this;
	}

	public StudentBuilder firstName(String firstName) {
	    this.firstName = firstName;
	    return this;
	}

	public StudentBuilder lastName(String lastName) {
	    this.lastName = lastName;
	    return this;
	}

	public StudentBuilder birthDate(LocalDate birthDate) {
	    this.birthDate = birthDate;
	    return this;
	}

	public StudentBuilder phone(String phone) {
	    this.phone = phone;
	    return this;
	}

	public StudentBuilder address(String address) {
	    this.address = address;
	    return this;
	}

	public StudentBuilder email(String email) {
	    this.email = email;
	    return this;
	}

	public StudentBuilder rank(String rank) {
	    this.rank = rank;
	    return this;
	}

	public StudentBuilder gender(Gender gender) {
	    this.gender = gender;
	    return this;
	}

	public StudentBuilder group(Group group) {
	    this.group = group;
	    return this;
	}

	public Student build() {
	    return new Student(id, studentId, firstName, lastName, birthDate, phone, address, email, rank, gender,
		    group);
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
	return String.format(
		"Student: studentId=%s  firstName=%s  lastName=%s  birthDate=%s  phone=%s  address=%s  email=%s  gender=%s  rank=%s  group=%s ",
		studentId, firstName, lastName, birthDate, phone, address, email, gender, rank,
		this.group != null ? this.group.getName() : "null");
    }

}
