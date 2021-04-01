package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class LessonsTimeSchedule {

    private int id;
    private String name;
    private List<LessonTime> lessonTimes = new ArrayList<>();

    public LessonsTimeSchedule() {
    }

    public LessonsTimeSchedule(int id, String name, List<LessonTime> lessonTimes) {
	this.id = id;
	this.name = name;
	this.lessonTimes = lessonTimes;
    }

    public static LessonsTimescheduleBuilder builder() {
	return new LessonsTimescheduleBuilder();
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

    public List<LessonTime> getLessonTimes() {
	return lessonTimes;
    }

    public void setLessonTimes(List<LessonTime> lessonTimes) {
	this.lessonTimes = lessonTimes;
    }

    public static class LessonsTimescheduleBuilder {

	private int id;
	private String name;
	private List<LessonTime> lessonTimes = new ArrayList<>();

	public LessonsTimescheduleBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public LessonsTimescheduleBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public LessonsTimescheduleBuilder lessonTimes(List<LessonTime> lessonTimes) {
	    this.lessonTimes = lessonTimes;
	    return this;
	}

	public LessonsTimeSchedule build() {
	    return new LessonsTimeSchedule(id, name, lessonTimes);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	result = prime * result + ((lessonTimes == null) ? 0 : lessonTimes.hashCode());
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
	LessonsTimeSchedule other = (LessonsTimeSchedule) obj;
	if (id != other.id)
	    return false;
	if (lessonTimes == null) {
	    if (other.lessonTimes != null)
		return false;
	} else if (!lessonTimes.equals(other.lessonTimes))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return name;
    }

}
