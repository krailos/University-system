package ua.com.foxminded.krailo.university.entities;

import java.util.ArrayList;
import java.util.List;

public class Building {

    private String id;
    private String name;
    private String address;
    private List<Audience> audiences = new ArrayList<>();

    public Building() {
    }

    public Building(String id, String name) {
	this.id = id;
	this.name = name;
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

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    @Override
    public String toString() {
	return name;
    }

}
