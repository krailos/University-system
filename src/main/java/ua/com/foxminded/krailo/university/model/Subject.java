package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "subjects")
@NamedQueries({ @NamedQuery(name = "SelectAllSubjects", query = "from Subject s order by s.name"),
	@NamedQuery(name = "SelectSubjectsByTeacher", query = "from Subject s inner join s.teachers as t where t.id = :teacherId order by s.name"),
	@NamedQuery(name = "SelectSubjectsByYear", query = "from Subject s inner join s.years as y where y.id = :yearId order by s.name"),
	@NamedQuery(name = "CountAllSubjects", query = "select count(id) from Subject")})
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    @ManyToMany(mappedBy = "subjects", fetch = FetchType.LAZY)
    private List<Teacher> teachers;
    @ManyToMany(mappedBy = "subjects", fetch = FetchType.LAZY)
    private List<Year> years;

    public Subject() {
    }

    public Subject(int id, String name, String description, List<Teacher> teachers, List<Year> years) {
	this.id = id;
	this.name = name;
	this.description = description;
	this.teachers = teachers;
	this.years = years;
    }

    public static SubjectBuilder builder() {
	return new SubjectBuilder();
    }

    public List<Year> getYears() {
	return years;
    }

    public void setYears(List<Year> years) {
	this.years = years;
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

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public List<Teacher> getTeachers() {
	return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
	this.teachers = teachers;
    }

    public static class SubjectBuilder {

	private int id;
	private String name;
	private String description;
	private List<Teacher> teachers = new ArrayList<>();
	private List<Year> years = new ArrayList<>();

	public SubjectBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public SubjectBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public SubjectBuilder description(String description) {
	    this.description = description;
	    return this;
	}

	public SubjectBuilder teachers(List<Teacher> teachers) {
	    this.teachers = teachers;
	    return this;
	}

	public SubjectBuilder years(List<Year> years) {
	    this.years = years;
	    return this;
	}

	public Subject build() {
	    return new Subject(id, name, description, teachers, years);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + id;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((teachers == null) ? 0 : teachers.hashCode());
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
	Subject other = (Subject) obj;
	if (description == null) {
	    if (other.description != null)
		return false;
	} else if (!description.equals(other.description))
	    return false;
	if (id != other.id)
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (teachers == null) {
	    if (other.teachers != null)
		return false;
	} else if (!teachers.equals(other.teachers))
	    return false;
	if (years == null) {
	    if (other.years != null)
		return false;
	} else if (!years.equals(other.years))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Subject [id=" + id + ", name=" + name + ", description=" + description + ", teachers=" + ((teachers == null) ? 0 : teachers.size())
		+ ", years=" + ((years == null) ? 0 : years.size()) + "]";
    }

   
}
