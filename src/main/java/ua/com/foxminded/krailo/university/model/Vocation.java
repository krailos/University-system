package ua.com.foxminded.krailo.university.model;

import java.time.LocalDate;

public class Vocation {

    private int id;
    private String kind;
    private LocalDate applyingDate;
    private LocalDate start;
    private LocalDate end;
    private Teacher teacher;

    public Vocation() {
    }

    public Vocation(int id, String kind, LocalDate applyingDate, LocalDate start, LocalDate end, Teacher teacher) {
	super();
	this.id = id;
	this.kind = kind;
	this.applyingDate = applyingDate;
	this.start = start;
	this.end = end;
	this.teacher = teacher;
    }

    public Vocation(String kind, LocalDate applyingDate, LocalDate start, LocalDate end, Teacher teacher) {
	super();
	this.kind = kind;
	this.applyingDate = applyingDate;
	this.start = start;
	this.end = end;
	this.teacher = teacher;
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
