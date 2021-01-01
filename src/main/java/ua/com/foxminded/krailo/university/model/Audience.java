package ua.com.foxminded.krailo.university.model;

public class Audience {

    private String number;
    private Building building;
    private Integer capacity;
    private String description;

    public Audience() {
    }

    public Audience(String number, Building building) {
	this.number = number;
	this.building = building;
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
	return description;
    }

}
