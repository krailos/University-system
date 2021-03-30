package ua.com.foxminded.krailo.university.model;

import java.util.ArrayList;
import java.util.List;

public class Timetable {

    private int id;
    private String name;
    private Student student;
    private Teacher teacher;
    private List<Lesson> lessons = new ArrayList<>();

    public Timetable() {
    }

    public Timetable(int id, String name, Student student, Teacher teacher, List<Lesson> lessons) {
	this.id = id;
	this.name = name;
	this.student = student;
	this.teacher = teacher;
	this.lessons = lessons;
    }
    
    public static TimetableBuilder builder () {
	return new TimetableBuilder();
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

    public List<Lesson> getLessons() {
	return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
	this.lessons = lessons;
    }

    public Student getStudent() {
	return student;
    }

    public void setStudent(Student student) {
	this.student = student;
    }

    public Teacher getTeacher() {
	return teacher;
    }

    public void setTeacher(Teacher teacher) {
	this.teacher = teacher;
    }

    public static class TimetableBuilder {

	private int id;
	private String name;
	private Student student;
	private Teacher teacher;
	private List<Lesson> lessons = new ArrayList<>();

	public TimetableBuilder id(int id) {
	    this.id = id;
	    return this;
	}

	public TimetableBuilder name(String name) {
	    this.name = name;
	    return this;
	}

	public TimetableBuilder student(Student student) {
	    this.student = student;
	    return this;
	}

	public TimetableBuilder teacher(Teacher teacher) {
	    this.teacher = teacher;
	    return this;
	}

	public TimetableBuilder withLessons(List<Lesson> lessons) {
	    this.lessons = lessons;
	    return this;
	}

	public Timetable build() {
	    return new Timetable(id, name, student, teacher, lessons);
	}

    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	result = prime * result + ((lessons == null) ? 0 : lessons.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
	result = prime * result + ((student == null) ? 0 : student.hashCode());
	result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
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
	if (student == null) {
	    if (other.student != null)
		return false;
	} else if (!student.equals(other.student))
	    return false;
	if (teacher == null) {
	    if (other.teacher != null)
		return false;
	} else if (!teacher.equals(other.teacher))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Timetable [id=" + id + ", name=" + name + ", student=" + student + ", teacher=" + teacher + ", lessons="
		+ lessons + "]";
    }

}