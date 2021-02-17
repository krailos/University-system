package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class Timetable {

    private int id;
    private String name;
    private Year year;
    private List<Lesson> lessons = new ArrayList<>();

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

    public Year getYear() {
	return year;
    }

    public void setYear(Year year) {
	this.year = year;
    }

    public List<Lesson> getLessons() {
	return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
	this.lessons = lessons;
    }

    public static class TimetableBuilder {

	private Timetable timetable;

	public TimetableBuilder() {
	    timetable = new Timetable();
	}

	public TimetableBuilder withId(int id) {
	    timetable.id = id;
	    return this;
	}

	public TimetableBuilder withName(String name) {
	    timetable.name = name;
	    return this;
	}

	public TimetableBuilder withYear(Year year) {
	    timetable.year = year;
	    return this;
	}

	public TimetableBuilder withLessons(List<Lesson> lessons) {
	    timetable.lessons = lessons;
	    return this;
	}

	public Timetable built() {
	    return timetable;
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	result = prime * result + ((lessons == null) ? 0 : lessons.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((year == null) ? 0 : year.hashCode());
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
	Timetable other = (Timetable) obj;
	if (id != other.id)
	    return false;
	if (lessons == null) {
	    if (other.lessons != null)
		return false;
	} else if (!lessons.equals(other.lessons))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	if (year == null) {
	    if (other.year != null)
		return false;
	} else if (!year.equals(other.year))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Timetable [id=" + id + ", name=" + name + ", year=" + year + ", lessons=" + lessons + "]";
    }

}
