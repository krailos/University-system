package ua.com.foxminded.krailo.university.entities;

public class Audience {

    private String name;
    private Integer capacity;
    private Building building;

    public Audience() {
    }

    public Audience(String name, Building building) {
	this.name = name;
	this.building = building;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Integer getCapacity() {
	return capacity;
    }

    public void setCapacity(Integer capacity) {
	this.capacity = capacity;
    }

    public Building getBuilding() {
	return building;
    }

    public void setBuilding(Building building) {
	this.building = building;
    }

    @Override
    public String toString() {
	return name;
    }

}
