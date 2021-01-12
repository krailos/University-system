package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class Building {

    private int id;
    private String name;
    private String address;
    private List<Audience> audiences = new ArrayList<>();

    public Building() {
    }

    public Building(String name, String address) {
	this.name = name;
	this.address = address;
    }

    public Building(int id, String name, String address) {
	this.id = id;
	this.name = name;
	this.address = address;
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

    public List<Audience> getAudiences() {
	return audiences;
    }

    public void setAudiences(List<Audience> audiences) {
	this.audiences = audiences;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    @Override
    public String toString() {
	return id + "-" + name + "-" + address;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((address == null) ? 0 : address.hashCode());
	result = prime * result + ((audiences == null) ? 0 : audiences.hashCode());
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
	Building other = (Building) obj;
	if (address == null) {
	    if (other.address != null)
		return false;
	} else if (!address.equals(other.address))
	    return false;
	if (audiences == null) {
	    if (other.audiences != null)
		return false;
	} else if (!audiences.equals(other.audiences))
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

}
