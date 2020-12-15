package ua.com.foxminded.krailo.domain;

import java.util.List;

public class Building {

    private String name;
    private String address;
    private List<Audience> audiences;

    public Building() {
    }

    public Building(String name) {
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

    @Override
    public String toString() {
	return name;
    }

}
