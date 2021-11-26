package ua.com.foxminded.krailo.university.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "holidays")
@NamedQueries({ @NamedQuery(name = "SelectHolidaysByDate", query = "from Holiday where date = :date"),
	@NamedQuery(name = "SelectAllHolidays", query = "from Holiday order by date") })
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "{notblank}")
    private String name;
    @NotNull(message = "{notnull}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    public Holiday() {
    }

    public Holiday(int id, String name, LocalDate date) {
	this.id = id;
	this.name = name;
	this.date = date;
    }

    public static HolidayBuilder builder() {
	return new HolidayBuilder();
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public LocalDate getDate() {
	return date;
    }

    public void setDate(LocalDate date) {
	this.date = date;
    }

    public static class HolidayBuilder {

	private int id;
	private String name;
	private LocalDate date;

	public HolidayBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public HolidayBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public HolidayBuilder date(LocalDate date) {
	    this.date = date;
	    return this;
	}

	public Holiday build() {
	    return new Holiday(id, name, date);
	}

    }

    @Override
    public String toString() {
	return id + "-" + name + "-" + date;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((date == null) ? 0 : date.hashCode());
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
	Holiday other = (Holiday) obj;
	if (date == null) {
	    if (other.date != null)
		return false;
	} else if (!date.equals(other.date))
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
