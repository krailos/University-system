package ua.com.foxminded.krailo.university.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name = "audiences")
@NamedQueries({ @NamedQuery(name = "SelectAudienceByNumber", query = "from Audience where number = :number"),
	@NamedQuery(name = "SelectAllAudiences", query = "from Audience order by number"),
	@NamedQuery(name = "CountAllAudiences", query = "select count(id) from Audience") })
public class Audience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "{notblank}")
    private String number;
    @Max(value = 300, message = "{max}")
    private int capacity;
    @Size(max = 100, message = "size")
    private String description;

    public Audience() {
    }

    public Audience(int id, String number, int capacity, String description) {
	this.id = id;
	this.number = number;
	this.capacity = capacity;
	this.description = description;
    }

    public static AudienceBuilder builder() {
	return new AudienceBuilder();
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

    public static class AudienceBuilder {
	private int id;
	private String number;
	private int capacity;
	private String description;

	public AudienceBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public AudienceBuilder number(String number) {
	    this.number = number;
	    return this;
	}

	public AudienceBuilder capacity(int capacity) {
	    this.capacity = capacity;
	    return this;
	}

	public AudienceBuilder description(String description) {
	    this.description = description;
	    return this;
	}

	public Audience build() {
	    return new Audience(id, number, capacity, description);
	}

    }

    @Override
    public String toString() {
	return "id=" + id + "; number=" + number + "; capacity=" + capacity + "; description=" + description;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
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
