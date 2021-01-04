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

}
