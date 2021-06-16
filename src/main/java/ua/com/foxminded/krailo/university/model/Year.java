package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "years")
@NamedQueries({ @NamedQuery(name = "SelectAllYears", query = "from Year y order by y.name") })
public class Year {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "years_subjects", joinColumns = { @JoinColumn(name = "year_id") }, inverseJoinColumns = {
	    @JoinColumn(name = "subject_id") })
    private List<Subject> subjects = new ArrayList<>();
    @Transient
    private List<Group> groups = new ArrayList<>();

    public Year() {
    }

    public Year(int id, String name, List<Subject> subjects, List<Group> groups) {
	this.id = id;
	this.name = name;
	this.subjects = subjects;
	this.groups = groups;
    }

    public static YearBuilder builder() {
	return new YearBuilder();
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

    public static class YearBuilder {

	private int id;
	private String name;
	private List<Subject> subjects = new ArrayList<>();
	private List<Group> groups = new ArrayList<>();

	public YearBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public YearBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public YearBuilder groups(List<Group> groups) {
	    this.groups = groups;
	    return this;
	}

	public YearBuilder subjects(List<Subject> subjects) {
	    this.subjects = subjects;
	    return this;
	}

	public Year build() {
	    return new Year(id, name, subjects, groups);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((groups == null) ? 0 : groups.hashCode());
	result = prime * result + id;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
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
	if (subjects == null) {
	    if (other.subjects != null)
		return false;
	} else if (!subjects.equals(other.subjects))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Year [id=" + id + ", name=" + name + ", subjects=" + subjects + ", groups=" + groups + "]";
    }

}
