package ua.com.foxminded.krailo.university.model;

public class Audience {

    private int id;
    private String number;
    private Building building;
    private int capacity;
    private String description;

    public Audience() {
    }

    public Audience(String number, Building building) {
	this.number = number;
	this.building = building;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getNumber() {
	return number;
    }

    public void setNumber(String number) {
	this.number = number;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public int getCapacity() {
	return capacity;
    }

    public void setCapacity(int capacity) {
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
	return id+"-"+number+"-"+building+"-"+capacity+"-"+description;
    }

}
