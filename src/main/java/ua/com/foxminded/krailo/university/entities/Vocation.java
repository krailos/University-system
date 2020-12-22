package ua.com.foxminded.krailo.university.entities;

import java.time.LocalDate;

public class Vocation {

    private String kind;
    private LocalDate applayingDate;
    private Teacher teacher;
    private LocalDate start;
    private LocalDate end;

    public Vocation() {
    }

    public Vocation(String kind, LocalDate applayingDate, Teacher teacher, LocalDate startVocation,
	    LocalDate endVocation) {
	this.kind = kind;
	this.applayingDate = applayingDate;
	this.teacher = teacher;
	this.start = startVocation;
	this.end = endVocation;
    }

    public String getKind() {
	return kind;
    }

    public void setKind(String kind) {
	this.kind = kind;
    }

    public LocalDate getApplayingDate() {
	return applayingDate;
    }

    public void setApplayingDate(LocalDate applayingDate) {
	this.applayingDate = applayingDate;
    }

    public Teacher getTeacher() {
	return teacher;
    }

    public void setTeacher(Teacher teacher) {
	this.teacher = teacher;
    }

    public LocalDate getStartVocation() {
	return start;
    }

    public void setStartVocation(LocalDate startVocation) {
	this.start = startVocation;
    }

    public LocalDate getEndVocation() {
	return end;
    }

    public void setEndVocation(LocalDate endVocation) {
	this.end = endVocation;
    }
    
    @Override
    public String toString() {
        return teacher.toString() + " " + kind + " from " + start + " till " + end;
    }

}
