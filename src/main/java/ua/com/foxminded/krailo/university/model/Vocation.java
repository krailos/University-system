package ua.com.foxminded.krailo.university.model;

import java.time.LocalDate;

public class Vocation {

    private int id;
    private String kind;
    private LocalDate applyingDate;
    private Teacher teacher;
    private LocalDate start;
    private LocalDate end;

    public Vocation() {
    }

    public Vocation(String kind, LocalDate applyingDate, Teacher teacher, LocalDate startVocation,
	    LocalDate endVocation) {
	this.kind = kind;
	this.applyingDate = applyingDate;
	this.teacher = teacher;
	this.start = startVocation;
	this.end = endVocation;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public LocalDate getStart() {
	return start;
    }

    public void setStart(LocalDate start) {
	this.start = start;
    }

    public LocalDate getEnd() {
	return end;
    }

    public void setEnd(LocalDate end) {
	this.end = end;
    }

    public String getKind() {
	return kind;
    }

    public void setKind(String kind) {
	this.kind = kind;
    }

    public LocalDate getApplyingDate() {
	return applyingDate;
    }

    public void setApplyingDate(LocalDate applyingDate) {
	this.applyingDate = applyingDate;
    }

    public Teacher getTeacher() {
	return teacher;
    }

    public void setTeacher(Teacher teacher) {
	this.teacher = teacher;
    }

    @Override
    public String toString() {
	return teacher.toString() + " " + kind + " from " + start + " till " + end;
    }

}
