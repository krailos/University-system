package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class UniversityOffice {

    private int id;
    private String name;
    private String address;
    private List<DeansOffice> deansOffices = new ArrayList<>();
    private List<Building> buildings = new ArrayList<>();
    private List<Holiday> holidays = new ArrayList<>();

    public UniversityOffice() {
    }

    public UniversityOffice(int id, String name, String address) {
	this.id = id;
	this.name = name;
	this.address = address;
    }

    public static UniversityOfficeBuilder builder() {
	return new UniversityOfficeBuilder();
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

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public List<Holiday> getHolidays() {
	return holidays;
    }

    public void setHolidays(List<Holiday> holidays) {
	this.holidays = holidays;
    }

    public List<Building> getBuildings() {
	return buildings;
    }

    public void setBuildings(List<Building> building) {
	this.buildings = building;
    }

    public List<DeansOffice> getDeansOffices() {
	return deansOffices;
    }

    public void setDeansOffices(List<DeansOffice> deansOffices) {
	this.deansOffices = deansOffices;
    }

    public static class UniversityOfficeBuilder {

	private int id;
	private String name;
	private String address;

	public UniversityOfficeBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public UniversityOfficeBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public UniversityOfficeBuilder address(String address) {
	    this.address = address;
	    return this;
	}

	public UniversityOffice build() {
	    return new UniversityOffice(id, name, address);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((address == null) ? 0 : address.hashCode());
	result = prime * result + ((buildings == null) ? 0 : buildings.hashCode());
	result = prime * result + ((deansOffices == null) ? 0 : deansOffices.hashCode());
	result = prime * result + ((holidays == null) ? 0 : holidays.hashCode());
	result = prime * result + id;
	result = prime * result + ((name == null) ? 0 : name.hashCode());
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
	UniversityOffice other = (UniversityOffice) obj;
	if (address == null) {
	    if (other.address != null)
		return false;
	} else if (!address.equals(other.address))
	    return false;
	if (buildings == null) {
	    if (other.buildings != null)
		return false;
	} else if (!buildings.equals(other.buildings))
	    return false;
	if (deansOffices == null) {
	    if (other.deansOffices != null)
		return false;
	} else if (!deansOffices.equals(other.deansOffices))
	    return false;
	if (holidays == null) {
	    if (other.holidays != null)
		return false;
	} else if (!holidays.equals(other.holidays))
	    return false;
	if (id != other.id)
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return name;
    }

}
