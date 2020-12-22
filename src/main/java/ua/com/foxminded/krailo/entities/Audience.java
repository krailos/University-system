package ua.com.foxminded.krailo.entities;

public class Audience {

    private String name;
    private Integer capacity;

    public Audience() {
    }

    public Audience(String name) {
	this.name = name;
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
    
    @Override
    public String toString() {
        return name;
    }

}
