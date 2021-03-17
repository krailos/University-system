package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class Faculty {

    private int id;
    private String name;
    private DeansOffice deansOffice;
    private List<Speciality> specialities = new ArrayList<>();

    public Faculty() {
    }

    public Faculty(int id, String name, DeansOffice deansOffice, List<Speciality> specialities) {
	this.id = id;
	this.name = name;
	this.deansOffice = deansOffice;
	this.specialities = specialities;
    }

    public static FacultyBuilder builder() {
	return new FacultyBuilder();
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

    public List<Speciality> getSpecialities() {
	return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
	this.specialities = specialities;
    }

    public DeansOffice getDeansOffice() {
	return deansOffice;
    }

    public void setDeansOffice(DeansOffice deansOffice) {
	this.deansOffice = deansOffice;
    }

    public static class FacultyBuilder {
	private int id;
	private String name;
	private DeansOffice deansOffice;
	private List<Speciality> specialities = new ArrayList<>();

	public FacultyBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public FacultyBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public FacultyBuilder deansOffice(DeansOffice deansOffice) {
	    this.deansOffice = deansOffice;
	    return this;
	}
	
	public FacultyBuilder specialities(List<Speciality> specialities) {
	    this.specialities = specialities;
	    return this;
	}

	public Faculty build() {
	    return new Faculty(id, name, deansOffice, specialities);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((deansOffice == null) ? 0 : deansOffice.hashCode());
	result = prime * result + id;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((specialities == null) ? 0 : specialities.hashCode());
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
	Faculty other = (Faculty) obj;
	if (deansOffice == null) {
	    if (other.deansOffice != null)
		return false;
	} else if (!deansOffice.equals(other.deansOffice))
	    return false;
	if (id != other.id)
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (specialities == null) {
	    if (other.specialities != null)
		return false;
	} else if (!specialities.equals(other.specialities))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return name;
    }

}
