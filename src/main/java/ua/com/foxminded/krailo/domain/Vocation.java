package ua.com.foxminded.krailo.domain;

import java.time.LocalDate;

public class Vocation {

    private String kind;
    private LocalDate applayingDate;
    private Teacher teacher;
    private LocalDate startVocation;
    private LocalDate endVocation;

    public Vocation() {
    }

    public Vocation(String kind, LocalDate applayingDate, Teacher teacher, LocalDate startVocation,
	    LocalDate endVocation) {
	this.kind = kind;
	this.applayingDate = applayingDate;
	this.teacher = teacher;
	this.startVocation = startVocation;
	this.endVocation = endVocation;
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
	return startVocation;
    }

    public void setStartVocation(LocalDate startVocation) {
	this.startVocation = startVocation;
    }

    public LocalDate getEndVocation() {
	return endVocation;
    }

    public void setEndVocation(LocalDate endVocation) {
	this.endVocation = endVocation;
    }
    
    @Override
    public String toString() {
        return teacher.toString() + " " + kind + " from " + startVocation + " till " + endVocation;
    }

}
