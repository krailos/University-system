package ua.com.foxminded.krailo.university.model;

public class Audience {

    private int id;
    private String number;
    private Building building;
    private int capacity;
    private String description;

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

    public static class AudienceBuilder {
	
	private Audience audience;

	public AudienceBuilder() {
	    audience = new Audience();
	}

	public AudienceBuilder withId(int id) {
	    audience.id = id;
	    return this;
	}

	public AudienceBuilder withNumber(String number) {
	    audience.number = number;
	    return this;
	}

	public AudienceBuilder withBuilding(Building building) {
	    audience.building = building;
	    return this;
	}

	public AudienceBuilder withCapacity(int capacity) {
	    audience.capacity = capacity;
	    return this;
	}

	public AudienceBuilder withDescription(String description) {
	    audience.description = description;
	    return this;
	}

	public Audience built() {
	    return audience;
	}

    }

    @Override
    public String toString() {
	return id + "-" + number + "-" + building + "-" + capacity + "-" + description;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((building == null) ? 0 : building.hashCode());
	result = prime * result + capacity;
	result = prime * result + ((description == null) ? 0 : description.hashCode());
	result = prime * result + id;
	result = prime * result + ((number == null) ? 0 : number.hashCode());
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
	Audience other = (Audience) obj;
	if (building == null) {
	    if (other.building != null)
		return false;
	} else if (!building.equals(other.building))
	    return false;
	if (capacity != other.capacity)
	    return false;
	if (description == null) {
	    if (other.description != null)
		return false;
	} else if (!description.equals(other.description))
	    return false;
	if (id != other.id)
	    return false;
	if (number == null) {
	    if (other.number != null)
		return false;
	} else if (!number.equals(other.number))
	    return false;
	return true;
    }

}
